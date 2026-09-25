# Pocket/src/main/java/com/pocket/sdk/util/view/tooltip/view/TooltipView.java
## What this is
The interface every tooltip bubble style implements: bind to a controller for dismissal, expose its Android view, position itself from anchor and window bounds, and animate in and out. It is the seam that lets new visual styles plug into `Tooltip` without touching placement or touch logic. `CaretTooltip` is the live implementation.
## How it fits
`Tooltip.Builder.addView()` collects these; `TooltipViewsHolder` calls `getView()` to lay them out, `applyAnchor()` to position each one per anchor move, and `animateIn()`/`animateOut()` on show and dismiss. `bind()` gives each view the controller so taps and buttons can dismiss.
## Key pieces
- `bind(controller)` — hands the view its dismiss handle. WHY: views dismiss through the controller so outside-touch accounting and listener callbacks stay consistent.
- `getView()` — the view itself, always the same object. WHY: the holder adds it once and repositions it on every anchor move.
- `applyAnchor(xy, anchorBounds, windowBounds)` — writes the desired x/y into `xy`, returning false when the style cannot fit at this anchor. WHY: returning false is the back-pressure that makes the whole tooltip retry or fail cleanly.
- `animateIn()` / `animateOut(callback)` — enter animation and exit animation that must fire the callback. WHY: the holder waits for every view's exit callback before removing the frame.
## Junior notes
- `applyAnchor()` must be side-effect-free on failure: leave the view where it was, since a later retry with a laid-out anchor may succeed.
- `animateOut()` must always invoke its callback, even if the view was never shown; the holder counts callbacks to know when removal is safe, and a missing call leaks the overlay frame.
