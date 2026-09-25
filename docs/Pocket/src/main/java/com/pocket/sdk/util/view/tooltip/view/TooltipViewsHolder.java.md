# Pocket/src/main/java/com/pocket/sdk/util/view/tooltip/view/TooltipViewsHolder.java
## What this is
The layout and touch manager behind a shown tooltip: it stacks the tooltip views in a full-screen `FrameLayout` (an Android layout that layers children on top of each other), positions each one from the anchor bounds on every move, runs enter/exit animations, and enforces the outside-touch policy (swallow vs pass-through, dismiss or not). One holder lives for exactly one tooltip show.
## How it fits
Created by `Tooltip.TooltipController` with the builder's views and a `ViewGroupDisplayer`; `showViews()` is called on every anchor move, `dismiss()` on every dismissal path. It owns the touch listener that implements `OutsideTouchAction` and the click proxy that turns bubble taps into anchor clicks.
## Key pieces
- `TooltipViewsHolder(context, views, window)` — builds the frame, adds each tooltip view invisible (so they can measure), and installs it via `setView()`. WHY: invisible-but-attached views report real sizes for first placement.
- `showViews(anchorBounds)` — converts the frame and anchor to screen coordinates, asks each view to `applyAnchor()`, and positions successes; first full success makes everything visible with `animateIn()`. Returns false if any view could not fit. WHY: atomic show avoids half-placed multi-view tooltips flashing.
- `dismiss()` — if never shown, removes the frame immediately; else runs every view's `animateOut()` and removes the frame when the last one finishes. WHY: exit animations complete before the overlay vanishes.
- Frame touch listener — on outside tap dismisses per policy (reporting `ANCHOR_CLICKED` vs `DISMISS_REQUESTED`) and swallows or passes the touch per `Block`. WHY: one place implements every touch policy combination.
- `setOutsideTouchAction(value, controller)` — installs the policy; for `IF_NOT_ON_ANCHOR` also installs a click listener that proxy-clicks the anchor when the press started on it. WHY: swallowing the touch is not enough; the anchor still needs its click.
## Junior notes
- `mAnchorBounds` hit-testing uses frame-local coordinates from the touch event; comparing them against screen coordinates would misclassify every tap, so keep the existing conversion.
- `showViews()` reuses recycled arrays and rects to avoid per-frame allocation during scroll-driven re-anchors; do not store references to them outside the call.
