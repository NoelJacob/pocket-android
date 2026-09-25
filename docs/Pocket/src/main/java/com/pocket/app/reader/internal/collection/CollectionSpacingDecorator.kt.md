# Pocket/src/main/java/com/pocket/app/reader/internal/collection/CollectionSpacingDecorator.kt
## What this is
Vertical (and on tablets, horizontal) breathing room between collection story cards. Two `RecyclerView.ItemDecoration`s (layout hooks that offset rows without touching the adapter): a simple bottom-margin decorator for the phone list and a two-column grid decorator for tablets.
## How it fits
Added to the story RecyclerView in `CollectionFragment.setupRecyclerView` — phones get `CollectionSpacingDecorator`, tablets get `CollectionGridSpacingDecorator` alongside their 2-column `GridLayoutManager`.
## Key pieces
- `CollectionSpacingDecorator` — 18dp bottom inset on every row via `DimenUtil.dpToPxInt`.
- `CollectionGridSpacingDecorator` — 18dp bottom inset plus a half-margin (9dp) gutter: left-column items get right padding, right-column items get left padding, determined by `parent.getChildAdapterPosition(view) % 2`.
## Junior notes
- The even/odd check assumes exactly 2 columns — if the span count ever changes, this decorator must change with it.
- Margins are split (half on each side of the gutter) rather than full-on-one-side so the outer edges stay flush with the screen padding.
