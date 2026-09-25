# Pocket/src/main/java/com/pocket/sdk/util/drawable/RainbowDrawable.java
## What this is
Pocket's signature four-color loading bar, drawn as an Android `Drawable` (a low-level 2D drawing object a view paints in `onDraw`). At rest it paints four static quarter segments (mint, teal, coral, gold); during loads it animates sliding color blocks with an accelerating interpolator. It also dims its colors in dark mode.
## How it fits
Hosted by `RainbowBar` (and the circular variant used in `FetchingDialog`'s loading screen), which sets its bounds and forwards state changes. `startProgressAnimation()` is called when a load begins and `stopProgressAnimation()` when it ends; each `draw()` ends with `invalidateSelf()` (request another frame) while animating, so the animation is driven by redraws.
## Key pieces
- `GREEN` / `BLUE` / `RED` / `GOLD` and `PAINT_*` — brand colors loaded once from resources into shared `Paint` objects. WHY: static paints avoid allocating drawing objects every frame.
- `AnimateState` (`IDLE`, `STARTING`, `ACTIVE`, `STOPPING_BEFORE_ACTIVE`, `STOPPING`) — phases that blend the static bar out, run the loop, then blend it back. WHY: starting/stopping mid-sweep does not visibly snap.
- `startProgressAnimation()` / `stopProgressAnimation()` — enter the loop immediately, or finish the current sweep before settling back to idle. WHY: the bar always comes to rest on the clean static rainbow.
- `draw(canvas)` — paints either the static quarters or the animated segments with 2dp dividers, cycling which color leads each lap. WHY: the moving gaps plus color rotation read as forward progress.
- `onStateChange(state)` — watches the view state for dark mode and drops paint alpha to 70 percent with a black backing rect. WHY: full-brightness brand colors vibrate on dark backgrounds.
- `onBoundsChange(bounds)` — rescales animation duration to the view width relative to a 320dp phone. WHY: the sweep speed looks constant on tablets and phones.
## Junior notes
- The constructor requires a `Callback` (normally the hosting view) because animation frames are delivered through `invalidateSelf()`; without it `startProgressAnimation()` silently paints nothing. The host must also implement `verifyDrawable()`.
- `setAlpha()` and `setColorFilter()` are intentional no-ops and the drawing assumes edge-to-edge bounds, so do not reuse this drawable where fading or padding is expected.
