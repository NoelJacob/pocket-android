# pocket-ui/src/main/java/com/pocket/ui/view/filter/FilterTile.kt

## What this is
A small selectable card showing a title over a description (for example a filter preset name plus its explanation). The user sees a rounded-corner tile (4dp radius, clipped to that outline) that reads as one tappable unit; both text lines share a single foreground color so the tile can be recolored as a whole for selected versus unselected states.

## How it fits
Used for filter/sort option grids where each choice is a tile. It extends ThemedConstraintLayout2 (the theme-aware ConstraintLayout variant) and inflates `ViewFilterTileBinding` (`view_filter_tile`: title + description) into itself. Hosts set it from layout XML via the `FilterTile` attributes or rebind the two labels from code/adapters; selection visuals are driven by the caller's foreground color and selected state, not by logic in this class.

## Key pieces
- `binding` (ViewFilterTileBinding): title and description views; the `init`-time `ViewOutlineProvider` (an object describing the view's shadow/clip shape) plus `clipToOutline = true` gives the 4dp rounded clipping.
- `titleText` / `descriptionText` attributes: set the two labels from XML (`getString` may return null, leaving the default layout text).
- `foregroundColor` attribute: a color state list applied to BOTH labels, so one attribute recolors the whole tile and can vary with state.
- `init` styled-attributes block: the only configuration logic; reads `R.styleable.FilterTile` and applies text plus colors.

## Junior notes
- There is no databinding adapter and no selected-state logic here: unlike PocketChip, tapping/selection and any icon swap must be implemented by the host (adapter or fragment).
- `foregroundColor` accepts a color or a reference (including a selector), which is how hosts get state-dependent text color without code.
- The rounded corners are a clip, not a background: the tile background itself must be set by the layout or host, or the 4dp outline clips content against a rectangular background.
