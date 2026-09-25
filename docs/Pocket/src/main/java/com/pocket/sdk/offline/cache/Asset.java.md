# Pocket/src/main/java/com/pocket/sdk/offline/cache/Asset.java

## What this is
A value object mapping one remote file (a URL) to its local cache path (a `File` under the offline directory). It also tags the kind of file: markup, image, or stylesheet. Equality is based on the local path, so two references to the same cached file collapse to one entry.

## How it fits
`WebDownloader` and `StreamingMarkupProcessor` discover URLs in HTML/CSS, call `Asset.create(...)` / `createImage(...)` with the app's `AssetDirectory` to compute where each file belongs, then hand the `Asset` to `Assets` (which tracks it in `AssetsDatabase`) and to `AssetDownloader` for fetching. The reader later loads HTML whose links were already rewritten to these local paths.

## Key pieces
- `url` / `local` / `type` / `filename` — the web address, the disk location, `IMAGE`/`MARKUP`/`STYLESHEET`, and the image filename workaround. WHY: together they are the full "remote ↔ local" mapping the cache needs.
- `MARKUP`, `IMAGE`, `STYLESHEET` constants — file kinds. WHY: the processor treats stylesheets recursively (they can import more files) and images as leaves.
- `create(URL, type, forceType, assetDirectory)` — builds the local path from the URL host/path with sanitizing, query cleanup, and length caps. WHY: URLs contain characters illegal in filenames and collisions must be avoided.
- `createImage(url, assetDirectory)` — convenience for the common image case. WHY: most assets discovered are images.
- `equals()` / `hashCode()` on `local` — dedupe by disk location. WHY: several pages can share one CSS file; it downloads once.

## Junior notes
- The `MARKUP` type is legacy: markup pages are stored per-item as `web.html`/`text.html`, not as generic assets. Do not add new `MARKUP` assets.
- Filename cleaning (`PATTERN_CLEAN_PATH`, `cleanUpQueries`, length limits) exists because web URLs are hostile filenames; never build cache paths by hand, always go through `create()`.
