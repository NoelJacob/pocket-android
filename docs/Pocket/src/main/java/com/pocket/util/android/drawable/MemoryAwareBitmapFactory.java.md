# Pocket/src/main/java/com/pocket/util/android/drawable/MemoryAwareBitmapFactory.java
## What this is
A bitmap decoder that survives low-memory conditions by retrying. It wraps `BitmapFactory` decodes (from file path, file descriptor, region decoder, or URL) with an out-of-memory recovery ladder: first a garbage collection, then trimming the image cache, then giving up and rethrowing. A `decodeQuietly` variant swallows failures and returns null instead.
## How it fits
Called by the image pipeline: `com.pocket.sdk.image.ImageCache` and `com.pocket.sdk.image.ImageResizer` decode downloaded image files through here instead of calling `BitmapFactory` directly. `decode(path, opts)` takes a per-file lock from `ImageCache.getImageFileLocks()` (via `FileLocks`, a file-path locking helper) so two threads never decode the same file at once. It produces plain `Bitmap` objects for `CacheableBitmapWrapper` and the cache.
## Key pieces
- `decodeQuietly(path, opts)` — WHY: fire-and-forget path for UI-adjacent code; returns null on OOM/IO/interrupt instead of crashing. Usage in words: call it when a missing image is acceptable.
- `decode(path, opts)` — WHY: locked, retrying file decode; the main entry point. Never call on the UI thread (main thread) because retries sleep up to seconds.
- `decodeFileDescriptor(fd, opts)` / `decodeRegion(...)` / `decodeURL(...)` — WHY: same retry ladder for already-open files, cropped regions (large images decoded tile by tile), and network streams.
- `handleOOME(oome, attempt)` — WHY: the recovery ladder itself; attempt 1 runs `System.gc()`, attempt 2 trims `ImageCache` plus GC, attempt 3+ rethrows.
- `waitBeforeRetry(attempt)` — WHY: sleeps `attempt` seconds so the garbage collector finishes before retrying.
## Junior notes
- `OutOfMemoryError` is an `Error`, not an `Exception`; catching it is normally taboo, but bitmap decoding on old devices is the rare justified case.
- The `decode(path)` method opens its own `FileInputStream` and always closes it plus releases the file lock in `finally`; callers must not close anything themselves.
- Coroutines (background tasks) note: this blocks the calling thread, so always call it from a worker thread or `Dispatchers.IO`.

