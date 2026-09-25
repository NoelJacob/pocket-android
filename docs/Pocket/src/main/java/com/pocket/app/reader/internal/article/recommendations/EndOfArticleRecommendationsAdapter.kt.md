# Pocket/src/main/java/com/pocket/app/reader/internal/article/recommendations/EndOfArticleRecommendationsAdapter.kt
## What this is
Renders the "more to read" card list at the bottom of an article. It is a `ListAdapter` (a RecyclerView adapter that auto-animates list changes via `DiffUtil`) showing one small card per recommendation: title, publisher, thumbnail, save button, and overflow menu.
## How it fits
Created by the article screen's end-of-article section. It observes `EndOfArticleRecommendationsViewModel.recommendations` (an observable state stream) and calls `submitList()` whenever new data arrives, so the list refreshes itself. Every tap is delegated back to the ViewModel: card tap opens the story (`onCardClicked`), save toggles the saved state (`onSaveClicked`), overflow opens the story menu (`onOverflowClicked`).
## Key pieces
- `RecommendationViewHolder.bind(state, position)` — binds one `CorpusItemUiState` to the reused `ViewHomeSlateMinorCardBinding` card layout: sets texts, save-button state, lazy-loaded thumbnail (`LazyBitmapDrawable` loads the image off the main thread), hides the collection label, and wires the three click listeners.
- `DIFF_CALLBACK` — tells the list how to animate updates: two rows are the same item when their `url` matches, and their contents match only when the whole `CorpusItemUiState` is equal, so a save-toggle rebinds just that card.
- `init` collector — uses `repeatOnCreated` (a helper that collects a flow only while the view is at least created, cancelling automatically to avoid leaks) to feed ViewModel emissions into `submitList()`.
## Junior notes
- `ListAdapter` + `submitList()` is the standard pattern for lists: never call `notifyDataSetChanged()` yourself here; just submit the new list and DiffUtil works out insertions and changes.
- The adapter holds a direct reference to the ViewModel rather than lambdas — common in this codebase, so click logic stays testable in the ViewModel.
