# pocket-ui/src/main/java/com/pocket/ui/view/PaletteView.java

## What this is
A debug-style swatch grid: it draws rows of small rounded color squares directly on a `Canvas` (the low-level 2D drawing surface), one square per color resource id it was given. For the user it looks like a paint-sample card — a grid of filled rounded rectangles. It does all its own measuring and drawing rather than composing child views, so it is cheap for showing a handful of theme colors at once.

## How it fits
A debug or style-preview screen calls `addRow(R.color.xxx, ...)` one or more times (each call appends a row of swatches) and the view invalidates and re-lays-out to fit. `clearRows()` empties the grid back to zero size. Sizing is driven by `getSuggestedMinimumWidth/Height`, computed from the longest row and row count times the swatch-plus-gap unit. `init()` reads the swatch size from `pkt_space_md`, the gap from `pkt_space_sm`, and fixes the corner radius at 3dp via `DimenUtil`.

## Key pieces
- `rows` (`List<int[]>` of resolved color ints): the model — WHY resolved ints (not resource ids) are stored is so `onDraw()` can set paint colors without resource lookups on every frame.
- `addRow(colorResIds...)` / `clearRows()`: mutate `rows`, then call `invalidate()` (redraw) plus `requestLayout()` (remeasure) — WHY both is that the content and the size change together.
- `getSuggestedMinimumWidth()` / `getSuggestedMinimumHeight()`: report the grid's natural size (longest row wide, all rows tall) so `wrap_content` parents size it correctly.
- `onDraw(canvas)`: walks rows left-to-right, top-to-bottom, drawing each swatch with `drawRoundRect()` into the reused `rect` object and advancing by swatch-plus-gap.
- `init()`: one-time setup of the antialiased fill `Paint`, swatch/gap pixels, and corner radius; called from all four constructors.

## Junior notes
- Plain `View` (not a themed base class) with `Paint.ANTI_ALIAS_FLAG` — smooth rounded corners require antialiasing, and `Style.FILL` means solid squares rather than outlines.
- The reused `rect` field avoids allocating a new `RectF` per swatch per frame — never allocate inside `onDraw()`, which runs at display refresh rates.
