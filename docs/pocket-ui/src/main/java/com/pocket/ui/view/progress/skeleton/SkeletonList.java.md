# pocket-ui/src/main/java/com/pocket/ui/view/progress/skeleton/SkeletonList.java
## What this is
A placeholder list shown while real list content loads: 20 identical shimmering SkeletonItemRow rows in a vertical list. It gives the user a sense of the list's shape before data arrives, instead of a blank screen or a single spinner.

## How it fits
Screens swap this in where a RecyclerView (a scrollable list widget that reuses row views) of items will appear, then replace it with the real list once data loads. It extends ThemedRecyclerView with a fixed vertical LinearLayoutManager and a private Adapter that always reports LIST_SIZE (20) rows of SkeletonItemRow. Each row shimmers via AbsSkeletonRow.

## Key pieces
- `init()` — sets fixed size, a vertical LinearLayoutManager, and the placeholder Adapter.
- Private `Adapter` / `ViewHolder` — creates a SkeletonItemRow per position, binds nothing, and returns 20 from `getItemCount()`.

## Junior notes
- `setHasFixedSize(true)` is a RecyclerView performance hint meaning rows are all the same size, so layout math can be skipped.
- This list never shows real data; when loading finishes the screen replaces the whole view, it does not rebind it.
