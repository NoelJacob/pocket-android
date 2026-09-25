# Pocket/src/main/java/com/pocket/app/reader/internal/article/highlights/HighlightSpacingDecorator.kt
## What this is
This is a `RecyclerView.ItemDecoration` (a hook that adds spacing around list rows without touching the row layouts) for the highlights bottom sheet list. It puts a standard gap under every highlight card, extra room above the first, and a large 100dp pad under the last so content is not hidden behind the sheet handle or navigation bar.
## How it fits
`HighlightsBottomSheetFragment.setupRecyclerView` adds it to the highlight list alongside `HighlightsAdapter`; the same pattern (a `*SpacingDecorator`) is reused for collections and end-of-article recommendations lists.
## Key pieces
- `getItemOffsets` — the single override: sets `outRect.bottom/top` via `DimenUtil.dpToPxInt` based on adapter position (first vs last vs middle). Position checks against `itemCount - 1` are what create the overscroll room.
## Junior notes
- `margin` is in dp (density-independent pixels, which scale with screen density) and converted at draw time — hardcoding px here would space differently on every device.
