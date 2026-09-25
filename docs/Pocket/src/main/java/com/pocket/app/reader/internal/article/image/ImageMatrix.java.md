# Pocket/src/main/java/com/pocket/app/reader/internal/article/image/ImageMatrix.java
## What this is
This extends Android's `Matrix` (the 3x3 transform describing image scale and translation) with knowledge of the bitmap's own size plus a friendly `Values` snapshot (x/y offset, scale, scaled and full dimensions). It exists so gesture and layout code can ask "where is the image and how big is it drawn" without re-reading raw matrix floats everywhere.
## How it fits
`GalleryImageView` keeps one as `mImageMatrix` for all pan/zoom math and passes it to `ImageViewer.onMove` and `MatrixAnimator` for bounds checks and settle animations; `set(src)` copies both the matrix and the bitmap size when a view takes over another's transform.
## Key pieces
- `getValues()` — pulls the matrix floats and returns the cached `Values` view (position from `MTRANS_X/Y`, scale from `MSCALE_X`, derived scaled sizes). The single accessor keeps scale/offset reads consistent across callers.
- `Values.getFramePadding(viewWidth)` — centers images narrower than the viewport by reporting the needed side pad; used to position the off-screen follower images correctly.
- `setBitmapSize / set(src)` — size must travel with the matrix copy, otherwise scaled-size math silently uses the previous photo's dimensions after a swipe.
## Junior notes
- Scale is read from `MSCALE_X` only, which assumes uniform (non-rotated, non-skewed) transforms — true for this gallery, but do not reuse this for rotated images without revisiting.
