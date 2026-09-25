# Pocket/src/main/java/com/pocket/app/reader/internal/collection/CollectionStoryAdapter.kt
## What this is
Renders the Collection's member-story cards: hero image, title, markdown excerpt, publisher, save toggle, and overflow button. Tapping a card opens the story; the save and overflow buttons delegate to the ViewModel.
## How it fits
Installed by `CollectionFragment` on the story RecyclerView. It self-subscribes to `CollectionViewModel.storyListUiState` (an observable state stream collected with `repeatOnCreated`, which auto-cancels when the view is destroyed) and calls `submitList()` on each emission. Excerpts are rendered through the shared `MarkdownFormatter` so links stay tappable; the parent collection URL arrives as `corpusRecommendationId` for overflow analytics.
## Key pieces
- `ViewHolder.bind(state)` — binds one `StoryUiState` to `ViewHomeHeroCardBinding`: title, publisher, lazily loaded image (`LazyBitmapDrawable` loads off the main thread), save state (reset with `clear()` first because rows are recycled), hides `timeToRead`, always shows the excerpt, and shows the collection label only for nested collections. Wires save/overflow/card taps to the ViewModel.
- `DIFF_CALLBACK` — rows are the same item by `title`, contents by full equality, so a save toggle rebinds just that card.
## Junior notes
- `saveLayout.bind().clear()` before `setSaved(...)` matters: without the reset, a recycled row can flash the previous card's save state.
- Identity by `title` (not URL) is load-bearing for the diff — duplicate titles in one collection would confuse animations, but story titles are unique in practice.
