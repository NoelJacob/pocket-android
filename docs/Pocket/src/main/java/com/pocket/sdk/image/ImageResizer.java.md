# Pocket/src/main/java/com/pocket/sdk/image/ImageResizer.java
## What this is
Background worker that turns an already-downloaded source file into a resized JPEG on disk, following an `ImageResizeRule`. It decodes efficiently (power-of-2 `inSampleSize` first, then a matrix scale), handles rotation/exif, and writes the sized file other requests look for.
## How it fits
Submitted by `ImageCache` to the `img-resize` pool after a download (or when a sized variant is missing). Reads `request.asset` as source and `request.resize` as the rule, writes the file at `request.assetSizedPath`, and reports success/failure to the callback `ImageCache` passed in. `isImageResized()` is the cheap "already done?" check callers use to skip this work.
## Key pieces
- `isImageResized(Request)` — returns true when no resize was requested or the sized file exists and is newer than the source; avoids redundant decode work.
- `backgroundOperation()` — the `TaskRunnable` entry point (a background task run on a worker thread): checks the sized file, decodes via `getResizedBitmap()`, writes to disk, recycles the bitmap, and flags the asset `.nf` if the source is unusable.
- `getResizedBitmap(...)` overloads — decode paths from file path or `Uri`, with optional background fill color; `getResizedBitmapQuietly()` swallows all errors and returns null for callers that already handle failure.
## Junior notes
- Large images are decoded with `BitmapRegionDecoder` and `inSampleSize` so only a downsampled version is ever in memory; decoding the full bitmap first would crash low-memory devices with `OutOfMemoryError`.
- Sized files are `<filename>_<w>-<h>.jpg`; PNG transparency is not preserved (background filled white), noted by the TODO on the file extension.
