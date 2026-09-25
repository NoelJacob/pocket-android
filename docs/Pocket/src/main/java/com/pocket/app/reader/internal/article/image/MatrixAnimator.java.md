# Pocket/src/main/java/com/pocket/app/reader/internal/article/image/MatrixAnimator.java
## What this is
This animates an `ImageMatrix` from its current transform to a settled one (snapped back into bounds, re-centered, or rescaled) over ~180ms with an ease-in-out interpolator. It exists so zoomed or dragged photos glide home on finger lift instead of jumping. It is a value calculator, not an Android `Animator` — the owning view applies each frame.
## How it fits
`GalleryImageView.snap` builds one via the static `getIfOutOfBounds(matrix, ...)` factory (which returns null when already settled, so no animation runs), then drives it per frame until `postAnimation` reports completion through `OnAnimationCompleteListener.onAnimatorCompleted`, which resets the touch state.
## Key pieces
- `getIfOutOfBounds` — the decision: computes distance-to-settle (x, y, scale with left/down/shrink direction flags) from the current values and bounds; null means "leave it alone". All bounds policy lives here.
- `postAnimation(matrix)` — advances `percentTime`, interpolates the traveled distance, applies scale about the start center plus translation, and fires completion exactly once at 100% (or when `finishInstantly` is set).
- `finishInstantly` — snaps to the destination on the next frame; used when a new gesture or image swap must cancel the glide without a visual jump-cut later.
## Junior notes
- `SNAP_DURATION = 180ms` is tuned to feel like a settle, not a transition — lengthening it makes every photo lift feel laggy.
- The start matrix is defensively copied (`new ImageMatrix(); set(...)`) so mid-animation matrix edits by the touch handler cannot corrupt the interpolation base.
