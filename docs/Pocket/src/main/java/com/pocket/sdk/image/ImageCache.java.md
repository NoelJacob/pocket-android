# Pocket/src/main/java/com/pocket/sdk/image/ImageCache.java
## What this is
The engine behind `Image`: a singleton (one app-wide instance, created automatically by Hilt DI, which means constructor parameters are provided automatically) that downloads images, stores them on disk, and serves resized copies. Callers never use it directly; they go through `Image.build(...)`. It owns an in-memory bitmap cache plus three background thread pools.
## How it fits
Created by Hilt with `AppThreads`, `Assets` (the offline file tracker), `UserAgent`, HTTP and cookie delegates. `Image.Request` objects arrive via `getImage()`; this class routes them: serve from memory cache if present, else check the disk `Asset`, else download through `pocket-image-cache.com` (see `ImageCacheHelper`), register the file with `Assets`, and hand off to `ImageResizer`. Results flow back through a `callback()` that decodes the bitmap and fires `Image.ImageReadyCallback`. Watches app lifecycle to trim on low memory and cancel/clear work on logout.
## Key pieces
- `getImage(Request)` — fast path: returns a cached `CacheableBitmapWrapper` immediately if `returnBitmap` is set; otherwise submits an `ImageTask` to the routing pool and returns null (the real result arrives via callback).
- `routing` / `downloading` / `resizing` pools — priority pools named `img-route`, `img-download`, `img-resize`; routing decides, downloading fetches, resizing runs `ImageResizer`.
- `writeImage(...)` — streams the HTTP body to disk with a size cap (`MAX_FILE_SIZE`, 4MB); oversized files are deleted and treated as permanent failures (flagged `.nf` via `Assets.makeNFFile` so they are never retried).
- `callback(Request, Result)` — skips the callback if `isImageRequestStillValid` is now false (e.g. scrolled away), otherwise decodes the sized file into memory, puts it in the `BitmapLruCache` (a least-recently-used memory cache that evicts old bitmaps first), and fires `onImageRequestFinished`.
- `onLogoutStarted()` / `trim()` — cancels all pools on logout and evicts the memory cache; trims cached bitmaps on low-memory signals. `FileLocks` (per-file locks) prevents two threads from writing/reading the same partial file.
## Junior notes
- 404/403/301 responses are treated as permanent failures (`.nf` marker file), so a broken image URL is only fetched once; `refresh=true` forces a re-download attempt.
- `exists()` takes a file lock before checking the disk, because another thread may be mid-write; without the lock you could decode a half-written file.
