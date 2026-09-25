# Pocket/src/main/java/com/pocket/app/reader/internal/collection/CollectionScreen.kt
## What this is
The contract for the Collection screen: what the fragment must supply (the collection URL), what story/error taps the ViewModel handles, and every one-shot outcome the screen can produce. No logic — it keeps `CollectionFragment`, `CollectionViewModel`, and `CollectionStoryAdapter` agreeing on names.
## How it fits
`CollectionViewModel` implements `Initializer`, `StoryInteractions`, and `ErrorInteractions`; `CollectionFragment` collects `Event` and navigates; the adapter calls the `StoryInteractions` methods on tap. `QueueManager` in `OpenUrl` is the reader's previous/next pager over the story list.
## Key pieces
- `Initializer.onInitialized(url)` — entry point carrying the collection's URL from nav args.
- `StoryInteractions` — `onSaveClicked` (toggle save), `onCardClicked` (open story), `onOverflowClicked` (story menu, with the parent collection URL as `corpusRecommendationId`).
- `ErrorInteractions.onRetryClicked()` — error-state retry button refetches the collection.
- `Event` — `ShowOverflowBottomSheet`, `OpenUrl(url, queueManager)`, `ShowSavedToast` / `ShowArchivedToast` / `ShowReAddedToast`, and `GoToSignIn`.
## Junior notes
- Toast events look redundant but each maps to a distinct string resource (added vs archived vs re-added) — the ViewModel picks the wording, the fragment just shows it.
- `ShowOverflowBottomSheet` reuses the home recommendation overflow sheet, which is why it carries a `corpusRecommendationId` even on a collection screen.
