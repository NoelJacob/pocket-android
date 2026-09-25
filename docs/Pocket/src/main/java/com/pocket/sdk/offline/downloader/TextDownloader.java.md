# Pocket/src/main/java/com/pocket/sdk/offline/downloader/TextDownloader.java

## What this is
The fetcher for article view (Pocket's cleaned-up text rendering of a saved page): it requests the parsed article markup from the server API, swaps resource placeholders for local file paths, warms the needed images through `ImageCache`, and writes the final `text.html` for the item. Images are best-effort — a missing image never fails the article.

## How it fits
Called by `OfflineDownloading`'s per-item `Downloader` when the ARTICLE view is missing. It syncs an `ArticleView` request (with Permanent Library auth hash params for premium-archived copies), collects `article.resources` + `item.images` URLs, blocks at most 2 minutes on a `CountDownLatch` (a one-shot gate that waits for N completions) while images cache, then calls `assets.writeMarkup(item, ARTICLE, markup, "UTF-8")` and returns `OfflineStatus.OFFLINE`. The reader later opens `AssetDirectory.pathForText(item)`.

## Key pieces
- `download(item, pocket, assets, userAgent, http, formFactor, imageCache, refresh)` — the whole flow; static-ish worker taking all deps as params. WHY: keeps it stateless and testable outside the singleton graph.
- ArticleView request builder (`url`, `formfactor`, `pl_i/pl_gu/pl_h/pl_u/pl_t`, `refresh`) — fetches server-parsed markup with permanent-copy auth. WHY: article text comes from Pocket's parser service, not the raw URL.
- `{%pkt_resource_path_N}` replacement with absolute `Asset` paths — inlines where bundled resources live. WHY: article HTML loads through JS whose base path is the app bundle, so relative links would resolve wrong; absolute paths are required here.
- Image warm-up via `imageCache.build(url, assetUser).cache(...)` + 2-minute latch — fetches images concurrently then proceeds regardless. WHY: offline articles must include images, but a slow host must not stall the queue forever.
- `415/413 → OfflineStatus.INVALID` mapping — server says "no parseable article". WHY: distinguishes permanently-unavailable text (do not retry) from transient network errors (rethrow → `FAILED`, retried later).

## Junior notes
- `refresh=false` with an existing `text.html` returns `OFFLINE` immediately without network. Pass `refresh=true` only for explicit user refresh.
- `AssetUser.forItem(time_added, idkey)` ties image lifetime to this save; images shared across saves are reference-counted, not duplicated.
- `local-vs-remote` for this file: remote = article-view API response; local = `RIL_pages/<id>/text.html` + `RIL_assets` images. Trace a save: sync → `OfflineDownloading` → here → `Assets.writeMarkup` → `offline_text=OFFLINE`.
