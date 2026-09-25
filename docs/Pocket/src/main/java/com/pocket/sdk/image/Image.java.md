# Pocket/src/main/java/com/pocket/sdk/image/Image.java
## What this is
Public entry point for loading article and thumbnail images. Callers use the `Builder` to describe what size they want, then either prefetch with `cache()` or get a bitmap back with `getNow()` / `getAsync()`. The `Request` snapshot freezes all of those choices for the background workers.
## How it fits
UI code (article views, lists, offline screens) calls `Image.build(url, user)` which grabs `ImageCache` and `AppThreads` from `App`. The builder hands a `Request` to `ImageCache.getImage()`, which downloads via the network and resizes via `ImageResizer`, then invokes the callback (`ImageLoadListener`, `RawImageLoadListener`, or `ImageCachedListener`) on the chosen `CallbackThread`.
## Key pieces
- `Builder` — configures one request: size rule (`fill` / `fit` / `fitWidth` / `fitHeight`), download on/off plus `DownloadAuthorization`, refresh, extras bundle, callback thread, and `RequestCancel`; `cache()` prefetches to disk without returning a bitmap.
- `SizedBuilder` — returned once a size is set; adds `getNow()` / `getRawNow()` (blocking) and `getAsync()` / `getRawAsync()` (callback). A size is required before a bitmap is decoded so a giant file is never loaded into memory by accident.
- `Request` — immutable snapshot of a builder plus `assetSizedPath` (the resized file name derived from the rule's width/height file names); carries the `ImageReadyCallback` used by `ImageCache` and `ImageTask` priority checks.
- `Result` — `SUCCESS`, `FAILED` (retryable/uncertain), `FAILED_PERMANENTLY` (e.g. bad URL, flagged `.nf` file).
- `Canceller` / `RequestCancel` — lets scrolling lists cancel off-screen requests; checked before decoding and before invoking callbacks, and drives `ImageTask` priority.
- `CallbackThread` — where the callback runs: `UI` (default, a databinding-safe main thread), `BACKGROUND`, or `ANY`.
## Junior notes
- Bitmaps come back wrapped in `CacheableBitmapWrapper` (a reference-counted bitmap holder). Call `setBeingUsed(false)` when done, or use `getRawAsync` only if you will recycle the copy yourself.
- `getNow()` blocks the calling thread with a `CountDownLatch` (a one-shot gate that pauses until the async load finishes); never call it on the UI thread.
