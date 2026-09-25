# Pocket/src/main/java/com/pocket/sdk2/view/LazyAssetBitmap.java
## What this is
A `LazyBitmap` adapter that loads an offline-cached article image on demand at exactly the pixel size the view needs. Given an image `Asset` (a downloaded file tracked by the offline cache) plus the `AssetUser` (which downloaded copy it belongs to), its `fill()` builds an image request, fetches/decodes it in the background, and delivers the bitmap — or nothing if the view was recycled. It bridges the offline image pipeline and list/reader image views.
## How it fits
Created by list and reader binding code with either a URL (which builds an `Asset` from the quiet asset directory) or an existing `Asset`. `fill()` is called by `LazyBitmap`-aware views during layout; it goes through `Image.build(...)` (the image pipeline: cache lookup → download → resize) with `DownloadAuthorization.ALWAYS` and a cancellation check, then `getAsync()` posts the decoded bitmap back via `loaded.onBitmapLoaded()`.
## Key pieces
- `LazyAssetBitmap(String url, AssetUser)` / `(Asset, AssetUser)` constructors — URL form for simple callers, asset form when the caller already resolved the cache entry. WHY: most screens only have the URL.
- `fill(widthPx, heightPx, Loaded, Canceller)` — zero-size guard, then async load at the requested size; `canceller` aborts recycled views, `AssetUser.forSession()` fallback covers logged-out/anonymous loads. WHY: exact-size decode saves memory, cancellation prevents wrong-image-in-recycled-row bugs.
## Junior notes
- The zero width/height early return is load-bearing: views call `fill()` before layout with 0 sizes — removing it would fire useless image requests on every bind.
- The bitmap callback runs off the main thread path of `getAsync`; the receiving view is responsible for posting to the UI thread if needed — check `LazyBitmap.Loaded` usage before assuming.
