# Pocket/src/main/java/com/pocket/app/reader/internal/article/recommendations/EndOfArticleRecommendationsViewModel.kt
## What this is
Supplies the "recommended stories" rail at the end of an article. It loads recommendations for the current article URL, exposes them as observable UI state, and translates taps (open, save, overflow menu) into navigation events the reader screen handles.
## How it fits
Created by Hilt DI (constructor params provided automatically) when the article screen starts; the article host calls `onInitialized(url)` with the open story's URL. Data comes from `RecommendationsRepository` (a flow of corpus recommendations plus a refresh call) and saves go through the `Save` use case and `ItemRepository`. It emits `ArticleScreen.Event` values (open new URL, open overflow sheet, go to sign-in) which the article fragment collects to navigate. `EndOfArticleRecommendationsAdapter` renders its `recommendations` flow.
## Key pieces
- `recommendations: StateFlow<List<CorpusItemUiState>>` — the card list the adapter renders; rebuilt from each repository emission. `StateFlow` is an observable state stream that always holds the latest value.
- `uiState: StateFlow<UiState>` — currently just `visible`, flipped true once a non-empty recommendation batch arrives so the section header appears.
- `onInitialized(url)` — stores the URL, starts collecting the recommendations flow (`setupFlow`), and kicks off a network refresh (`refreshData`, failures only logged).
- `onCardClicked(url, corpusRecommendationId)` — emits `OpenNewUrl` carrying a `UrlListQueueManager` built from all current recommendation URLs, so the reader's previous/next pager can swipe through them.
- `onSaveClicked(url, isSaved, corpusRecommendationId)` — note the inverted-looking logic: the boolean arrives as the *new* desired state, so `isSaved=true` means "already saved, now unsave" and calls `itemRepository.delete()`; otherwise it runs `save(url)` in a coroutine (a background task) and routes logged-out users to sign-in.
- `onOverflowClicked(...)` — emits `OpenOverflowBottomSheet` with url, title, and recommendation id for the overflow menu.
- `CorpusItemUiState` — flattened card data (title, publisher, excerpt, imageUrl, url, isSaved, corpusRecommendationId) mapped from the repository's corpus models.
## Junior notes
- `MutableSharedFlow` with `extraBufferCapacity = 1` is used for one-shot navigation events (they fire once and are not state); `tryEmit` is fire-and-forget while `emit` suspends — both appear here.
- `onArticleViewed` is intentionally empty (analytics hook left for later); don't delete it, the `EndOfArticleRecommendationInteractions` interface requires it.
