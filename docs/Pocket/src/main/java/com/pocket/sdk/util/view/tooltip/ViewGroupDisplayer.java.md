# Pocket/src/main/java/com/pocket/sdk/util/view/tooltip/ViewGroupDisplayer.java
## What this is
The standard `ViewDisplayer`: shows tooltip views by adding them full-size to a `ViewGroup` (any Android layout that can contain child views), and removes them on dismiss. It is the bridge between the tooltip system and the normal view hierarchy.
## How it fits
Instantiated inside `Tooltip.TooltipController` with the display parent (explicit `setDisplayLocation()` or the anchor's content root) and given to `TooltipViewsHolder`, which installs its touch-handling `FrameLayout` through `setView()`. Dismissal removes that frame from the parent.
## Key pieces
- `ViewGroupDisplayer(parent)` — binds to the host layout. WHY: tooltips overlay existing content rather than replacing it, so they need a host to attach to.
- `setView(view)` — adds the tooltip frame at `MATCH_PARENT` (fill the parent) in both dimensions. WHY: full-size frame lets the holder position bubbles anywhere and intercept outside touches across the whole area.
- `dismiss()` — hides the frame and removes it from the parent. WHY: hiding first avoids a one-frame flash before removal.
## Junior notes
- The parent must be a layout that allows absolute positioning of children (a `FrameLayout`-like host); in a `LinearLayout` the match-parent frame would push content around instead of overlaying.
- `dismiss()` assumes `setView()` ran first; the holder always guarantees that order, so do not reuse a displayer across tooltips.
