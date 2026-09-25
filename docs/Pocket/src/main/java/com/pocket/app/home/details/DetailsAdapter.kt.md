# Pocket/src/main/java/com/pocket/app/home/details/DetailsAdapter.kt
## What this is
This is the RecyclerView adapter for the details screen (the full list of stories behind one slate or topic). It is a `ListAdapter`, which means it takes a list of items and animates only the rows that changed, using the `DIFF_CALLBACK` at the bottom. Each row is a hero card (`ViewHomeHeroCardBinding`) showing title, publisher, read time, image, and save/overflow buttons.
## How it fits
`DetailsFragment.setupRecyclerView()` creates this adapter and hands it the screen's `DetailsViewModel`. The adapter's `init` block collects `viewModel.uiState` (an observable state stream; collection is scoped to the view's `CREATED` lifecycle via `repeatOnCreated`, a helper that launches a coroutine tied to the lifecycle) and calls `submitList()` whenever the recommendations change. Taps are forwarded back into the ViewModel: card tap to `onItemClicked` (opens the reader), save button to `onSaveClicked`, overflow icon to `onOverflowClicked`.
## Key pieces
- `DetailsAdapter(viewLifecycleOwner, viewModel)` — constructor; the `init` block subscribes to `uiState.recommendations` so the fragment never pushes data manually.
- `ViewHolder.bind(state, position)` — fills one card from a `RecommendationUiState`; hides the read-time and collection label when empty/false, loads the thumbnail lazily (`LazyAssetBitmap`/`LazyBitmapDrawable` defer image loading until needed), and wires the three click listeners.
- `DIFF_CALLBACK` — identity is `itemId`, contents compare the whole data class; this is what lets `ListAdapter` animate inserts/removes instead of redrawing everything.
## Junior notes
- `saveLayout.bind().clear()...` is a builder chain on the custom `SaveButton` view; it must be `clear()`ed because view holders are recycled and old listeners would leak into the next row.
- The save click listener returns `state.isSaved` (the pre-click value) — the button uses that return to decide its optimistic visual state; the real state arrives later via the `uiState` flow.

