# Pocket/src/main/java/com/pocket/app/home/details/DetailsViewModel.kt
## What this is
This is the abstract ViewModel shared by the slate-details and topic-details screens. (A ViewModel survives screen rotation and holds UI state plus user actions, keeping them out of the fragment.) It owns the screen state (`UiState`), one-shot navigation events (`Event`), and the two actions common to both screens: saving/unsaving a story and opening its overflow menu. Subclasses only add data loading, retry, and tap-to-open behavior.
## How it fits
`DetailsFragment` observes `uiState` (a `StateFlow`, i.e. an observable holder that always has a current value) to render the list, and `events` (a `SharedFlow`, i.e. a stream of one-shot happenings with no current value) to navigate. Save taps go to `ItemRepository.delete()` for unsaving, or to the `Save` use case (a small injectable business-logic class) for saving. The `Save` use case reports `NotLoggedIn`, which becomes a `GoToSignIn` event that the fragment turns into `AuthenticationActivity`.
## Key pieces
- `UiState` — title, recommendations list, `ScreenState` (Loading vs Recommendations), plus error-snackbar fields used mainly by the topic screen.
- `ScreenState` — carries `loadingVisible` / `recommendationsVisible` booleans that the XML layout binds to directly (databinding = XML layouts bound to ViewModel fields, so visibility needs no fragment code).
- `Event` (`GoToReader`, `GoToSignIn`, `ShowRecommendationOverflow`) — navigation happens through these, never by calling the fragment directly, so rotation can't double-fire it.
- `onSaveClicked(url, isSaved, corpusRecommendationId)` — unsave is a synchronous repository delete; save runs in `viewModelScope` (a coroutine scope tied to the ViewModel's lifetime; coroutines = background tasks) because it can fail with `NotLoggedIn`.
- `DetailsInteractions` — the interface the adapter and databound layout call; keeps the XML decoupled from the abstract class.
## Junior notes
- `extraBufferCapacity = 1` on `_events` plus `tryEmit` means an event is never lost if it fires while the fragment isn't collecting yet, but two rapid events can drop the older one — fine for navigation, not for data.
- `onErrorRetryClicked` is abstract here because only the topic screen loads over the network; the slate screen's override is intentionally a no-op.
- `corpusRecommendationId` is analytics-only plumbing (which recommendation slot was tapped); it doesn't affect what the user sees.

