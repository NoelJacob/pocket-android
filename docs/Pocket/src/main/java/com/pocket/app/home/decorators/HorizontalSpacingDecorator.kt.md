# Pocket/src/main/java/com/pocket/app/home/decorators/HorizontalSpacingDecorator.kt
## What this is
A RecyclerView `ItemDecoration` (list-level spacing plugin, no row-layout changes) for horizontal rails: a left gap on every item, a right gap on the last item only, and optional extra edge margins on the first/last items, defaulting to 18dp gaps. It gives rails breathing room while aligning their edges with the rest of the screen.
## How it fits
Used on the recent-saves rail in `HomeFragment`, which constructs it with tablet edge compensation (`extraMarginOnFirstAndLast`) and re-adds it on rotation. Position comes from `getChildAdapterPosition` with `adapter.itemCount` marking the end.
## Key pieces
- `margin` / `extraMarginOnFirstAndLast`: base gap vs additional edge inset; WHY two knobs is edge alignment (screen margins, max-width centering) varies by device while inter-item rhythm stays constant.
- `getItemOffsets`: uniform left gap, last-item right gap, first-item boosted left; all via `DimenUtil` dp conversion.
## Junior notes
- `getChildAdapterPosition` can return NO_POSITION during animations: the comparisons then simply miss (no crash), but do not cache positions or branch critical logic on them.
