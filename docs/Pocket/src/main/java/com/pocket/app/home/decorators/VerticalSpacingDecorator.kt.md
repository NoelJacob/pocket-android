# Pocket/src/main/java/com/pocket/app/home/decorators/VerticalSpacingDecorator.kt
## What this is
A RecyclerView `ItemDecoration` (list-level spacing plugin, no row-layout changes) for vertical stacked cards: equal left, right, and bottom spacing, defaulting to 18dp. The simplest of the three decorator siblings; top spacing comes from the list's own padding.
## How it fits
Added to vertical card lists on Home-family screens where rows should float with uniform gutters, complementing the grid and horizontal-rail decorators used by sibling lists.
## Key pieces
- `margin` (18f default): the single dp knob applied to left/right/bottom via `DimenUtil`.
- `getItemOffsets`: writes `outRect` uniformly; no position logic, so every row is treated identically.
## Junior notes
- No top offset is intentional: pair with list `paddingTop` (or a header) rather than "fixing" the first row here, or double spacing appears when the list already pads.
