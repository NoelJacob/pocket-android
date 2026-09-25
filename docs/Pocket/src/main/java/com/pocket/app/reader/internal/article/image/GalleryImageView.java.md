# Pocket/src/main/java/com/pocket/app/reader/internal/article/image/GalleryImageView.java
## What this is
This is the zoomable/pannable image view behind the article image gallery: an `ImageView` locked to `ScaleType.MATRIX` (pixels positioned by an explicit transformation matrix) with its own drag, pinch-zoom, swipe-to-next, and snap-back touch handling. Users see a photo they can pinch to 3x, drag around, and fling sideways to move between article images.
## How it fits
`ImageViewer` hosts three of these (left/center/right) and implements `OnMoveListenser`; the center view reports drags via `onMove` so followers track it, and edge swipes call `shift(direction)` to rotate the carousel. `MatrixAnimator` handles the snap-back/settle animation on finger lift, reporting completion through `onAnimatorCompleted`.
## Key pieces
- `onTouch` (the `OnTouchListener`) — the gesture state machine (`TOUCH_DRAG/ZOOM/UP_NOW_SNAPPING_BACK`) built on `WrapMotionEvent` for multi-touch; thresholds differ zoomed-in vs out (`SHIFT_THRESHOLD_*`) so swipes past the edge change image only when intended.
- `snap(animate)` — asks `MatrixAnimator.getIfOutOfBounds` whether the image is off-screen or mis-scaled and animates it home; this is what settles the photo after every gesture.
- `setImage` — recycles the previous bitmap, resets the matrix, and calls `fitToScreen` so each new photo starts fitted; `setAsCenterImage` toggles clickability and the move listener so only the center view drives the carousel.
## Junior notes
- `onSizeChanged` re-fits and re-checks bounds, so rotation mid-zoom re-seats the image instead of leaving it stranded off-screen.
- `MAX_ZOOM_SCALE = 3` caps pinch zoom; `mBitmapThumbScale` is the floor — zoom-out past the fit scale snaps back rather than shrinking into a corner.
