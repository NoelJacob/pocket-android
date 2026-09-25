# Pocket/src/main/java/com/pocket/app/home/decorators/GridSpacingDecorator.kt
## What this is
A RecyclerView `ItemDecoration` (a list-level plugin that injects spacing around rows without touching row layouts) for 2-column grids: full outer margins, half-size inner gutter, and bottom spacing, defaulting to 18dp. Applied to a grid list once, it spaces every item correctly by column.
## How it fits
Added to grid RecyclerViews on Home-family screens alongside the horizontal (rails) and vertical (stacked cards) siblings. `getItemOffsets` writes each item's `outRect` (the spacing rectangle the list adds around the row) based on even/odd adapter position.
## Key pieces
- `margin` (18f default): single knob in dp; WHY one value is the outer/half/double relationships stay proportional automatically.
- `getItemOffsets`: left/right split by `position % 2` (left column gets outer-left + half-right and vice versa) plus uniform bottom; dp converted via `DimenUtil`.
## Junior notes
- Even/odd position assumes exactly 2 columns and no full-span headers: inserting a header row shifts parity for everything below it, so use a different decorator (or span-aware offsets) on grids with headers.
