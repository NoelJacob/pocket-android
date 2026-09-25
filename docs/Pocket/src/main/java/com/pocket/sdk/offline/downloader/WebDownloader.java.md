# Pocket/src/main/java/com/pocket/sdk/offline/downloader/WebDownloader.java

## What this is
The fetcher for web view (the original page as published): it downloads the raw HTML, rewrites every image/CSS link to a local relative path, downloads those assets (recursing into stylesheets that import more files), and saves the final `web.html`. Because live pages fail in fuzzy ways, it returns a `Result` hierarchy telling the caller which `OfflineStatus` to record and whether a retry makes sense.

## How it fits
Called by `OfflineDownloading`'s per-item `Downloader` when the WEB view is missing (today only via explicit `download()`, since prefetch is article-only). It streams the page through `StreamingMarkupProcessor` with an `AssetHandler`, fans asset fetches out via `AssetDownloader` on the `Worker` pool, writes everything through `Assets`, and reports back. Permanent-library (`PermanentLibraryUtil`) params authenticate archived-copy fetches for premium users.

## Key pieces
- `download(item, refresh, assets, ..., http, cookies, worker, cancel)` — orchestrates fetch → process → fan-out → write. WHY: one place owns the 5-step web pattern (fetch markup, find assets, rewrite links, download recursively, complete).
- `Result` subclasses (success / partial / failure variants) — graded outcomes with status + retry recommendations. WHY: a page with 9/10 images is usable (`PARTIAL`), a 404 is permanent (`INVALID`), a timeout is retryable (`FAILED`).
- `Downloader` interface + `Worker` interface — injectable HTTP fetch and thread-pool fan-out. WHY: lets the coordinator supply priorities and lets tests stub the network.
- `AssetDownloader` + `AssetHandler` collaboration — `AssetHandler.capture()` maps each URL to an `Asset` + relative path while `AssetDownloader` fetches/awaits them with caps (`maxStylesheets`, `maxImages`, `maxFileSize`). WHY: bounds an adversarial page (hundreds of images, giant CSS) so one save cannot eat the cache.
- Cookie delegate + `EclecticHttp` usage — replays login cookies for paywalled pages. WHY: some articles only render with the user's session.

## Junior notes
- Offline flow in plain terms: remote = live HTML+CSS+images; local = `RIL_pages/<id>/web.html` with rewritten relative links into shared `RIL_assets/`. The reader opens the local file with no network.
- Size/count caps mean big pages legitimately finish `PARTIAL`: do not "fix" that by raising caps without checking cache-limit behavior.
- Always thread the same `Cancel` through fetch, process, and await stages, or Wi-Fi-loss cancellation leaves orphan workers running.
