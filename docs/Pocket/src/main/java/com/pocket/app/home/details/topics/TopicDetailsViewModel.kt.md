# Pocket/src/main/java/com/pocket/app/home/details/topics/TopicDetailsViewModel.kt
## What this is
This is the ViewModel behind the topic-details screen. It loads one topic's story feed from the network, merges the curated and algorithmic sections into a single list, and exposes retry/error-snackbar state for when the fetch fails.
## How it fits
`TopicDetailsFragment` calls `onInitialized(topicId)` once. That sets the snackbar's error message, starts `setupTopicCollector()` (which resolves the display title from `TopicsRepository.getTopicsLocal()` and then collects `getTopicAsFlow(topicId)` for live updates), and fires `refreshTopic()` (which calls `topicsRepository.refreshTopic(topicId)` over the network in `viewModelScope`). Success flips the screen to `Recommendations` and hides the snackbar; failure shows it. `onErrorRetryClicked` sets the refreshing spinner and retries.
## Key pieces
- `onInitialized(topicId)` — stores the ID, then launches both the local collector and the network refresh in parallel so cached items (if any) paint fast.
- `setupTopicCollector()` — builds one list as curated items followed by algorithmic items (`mapNotNull { it.item }` skips entries with no article), mapping each to `RecommendationUiState` with its position index.
- `refreshTopic()` — try/catch around the network call; on exception sets `errorSnackBarVisible = true` (the `println` is debug logging worth removing later).
- `TopicDetailsInteractions` — adds `onInitialized(topicId)` to the shared `DetailsInteractions` contract.
## Junior notes
- The title lookup (`topics?.find { it.topic == topicId }`) can yield `""` if the local topic list hasn't loaded — the title fills in on a later emission, so don't "fix" it by blocking.
- Curated-then-algorithmic ordering is deliberate product behavior; don't sort or dedupe without checking with design.
- `onItemClicked` emits only `GoToReader(url)` — position/corpus ID are ignored here since topic analytics don't need them.

