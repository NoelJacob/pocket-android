# pocket-ui/src/main/java/com/pocket/ui/view/progress/RainbowProgressCircleView.java
## What this is
Pocket's branded loading spinner: a circle drawn with a sweeping arc that rotates and cycles through teal, coral, and amber colors. It works in two modes: indeterminate (endless spinning, the default) and determinate (shows 0-100% progress via `setProgress`). It draws itself with plain Canvas paint code, not an image asset.

## How it fits
Used anywhere loading is shown, notably inside FullscreenProgressView, and dropped into XML layouts directly. Callers use `setProgress(float)` to switch to determinate mode with an animated fill, or `setProgressIndeterminate(true)` to go back to spinning. The XML attributes `progressColorsExcludeCoral` and `progressStartAsArc` tweak the palette and the startup look.

## Key pieces
- `setProgress(float)` — switches off indeterminate mode and animates the arc to the new fraction over 400ms with a decelerating interpolator.
- `setProgressIndeterminate(boolean)` — picks spinning versus progress-fill mode.
- `startAnimation()` / `cancelAnimation()` — two infinite ValueAnimators (sweep angle at 1250ms, rotation at 1750ms) started only while the view is attached and visible; `updateAnimationStatus()` guards this so off-screen spinners cost nothing.
- `onDraw()` — draws the growing arc in the current theme-aware color plus the dimmer remainder arc, rotating the canvas each frame; each sweep cycle advances to the next color.
- `mColorStateLists` — theme-aware (light/dark) color list per arc color, resolved against the current drawable state so dark mode just works.

## Junior notes
- Animators are lifecycle-tied: `onAttachedToWindow`, `onDetachedFromWindow`, `setVisibility`, and `onVisibilityChanged` all funnel into `updateAnimationStatus()`, so you never need to start/stop it manually.
- `onDraw` returns early when no animator is running; `invalidate()` on each animation tick is what drives the redraw loop.
