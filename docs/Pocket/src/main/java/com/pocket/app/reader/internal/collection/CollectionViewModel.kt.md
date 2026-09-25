# Pocket/src/main/java/com/pocket/app/reader/internal/collection/CollectionViewModel.kt
## What this is
Loads a Pocket Collection (title, authors, intro, member stories) and drives everything on the Collection screen: loading/error/content states, the story list, per-story save toggles, card/overflow taps, and the reader toolbar (save/archive/share/overflow) for the collection itself.
## How it fits
Hilt-provided to `CollectionFragment`, which calls `onInitialized(url)` with the nav-arg URL. Data comes from `CollectionRepository.getCollection(url)`; saves go through the `Save` use case and `ItemRepository`; toolbar state reads the saved item via `ArticleRepository`/`ItemRepository` through the shared `ReaderToolbarDelegate`. One-shot `CollectionScreen.Event`s (open URL with a pager queue, overflow sheet, toasts, sign-in) go to the fragment; `CollectionStoryAdapter` renders `storyListUiState`.
## Key pieces
- `uiState: StateFlow<UiState>` — `screenState` (`Loading` / `Error` / `Default`, each precomputing which layout section is visible) plus title, author string, and intro markdown. `StateFlow` is an observable state stream holding the latest value.
- `storyListUiState: StateFlow<List<StoryUiState>>` — flattened per-story rows (title, publisher, excerpt, isSaved, imageUrl, url, collectionLabelVisible).
- `fetchCollection()` / `updateCollection(collection)` — sets Loading, fetches in a coroutine (a background task), maps stories to rows and header fields on success, sets Error (and logs) on failure; `onRetryClicked` just re-runs the fetch.
- `onSaveClicked(url)` — toggles: saved stories are deleted via `itemRepository`, unsaved ones go through `save(url)` with a sign-in detour for logged-out users; either way the row is optimistically flipped in place so the UI responds instantly.
- `onCardClicked(url)` — emits `OpenUrl` with a `UrlListQueueManager` over all story URLs so the reader pager can swipe between them.
- `Toolbar` inner class — extends `ReaderToolbarDelegate` with collection-specific states: unsaved collections show Save, saved ones show Archive/ReAdd, overflow exposes favorite/tags/delete/mark-as-not-viewed; archive/re-add also fire their toasts.
## Junior notes
- The optimistic save flip updates `_storyListUiState` directly without waiting for the repository — a later repository refresh corrects it if the write failed.
- `toolbar.url` must be set in `setupToolbar(url)` before any toolbar action; toolbar taps before init would act on a blank URL.
