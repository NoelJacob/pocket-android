# Pocket/src/main/java/com/pocket/app/reader/internal/article/image/CachedImage.java
## What this is
This loads one article image from the offline asset cache on disk and hands back a `Bitmap` (Android's in-memory image). It wraps the cached file path with a `SoftReference` (a memory-sensitive pointer the garbage collector may clear under pressure) plus a lock, so repeat views of the same image are cheap but never pin large bitmaps forever.
## How it fits
`ImageViewer.setImages` builds one `CachedImage` per article `Image` (skipping URLs with no offline asset); `getImage(index)` calls `get()` to decode on demand as the user swipes. Failures (missing asset, out of memory) yield null or an exception at construction rather than crashing the viewer.
## Key pieces
- `CachedImage(url)` — resolves the offline `Asset` for the URL and records its local path; throws `IllegalArgumentException` when nothing is cached, which is how uncached images are filtered out of the gallery.
- `get / getCachedBitmap / getFromDisk` — memory-first then disk: returns the live soft-referenced bitmap if present and not recycled, else decodes the file with `ARGB_8888`. The recycled check matters because `GalleryImageView.setImage` recycles replaced bitmaps.
## Junior notes
- `SoftReference` is not a real cache: under memory pressure the bitmap vanishes and the next `get()` re-decodes from disk — expect repeated disk reads on low-memory devices.
- `OutOfMemoryError` is caught and logged to null rather than thrown; callers must handle a null bitmap (blank image) as a normal outcome.
