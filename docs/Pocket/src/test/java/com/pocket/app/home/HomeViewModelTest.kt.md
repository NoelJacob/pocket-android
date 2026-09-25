# Pocket/src/test/java/com/pocket/app/home/HomeViewModelTest.kt
## What this is
Tests for `HomeViewModel`, the Home feed screen logic. It proves tapping a recommendation sends a `slateArticleImpression` analytics event, and that empty slates (sections with no recommendations) are filtered out of `slatesUiState` before the UI renders.
## How it fits
Guards production `com.pocket.app.home.HomeViewModel`, fed by `HomeRepository` (slate lineup), `TopicsRepository`, `ItemRepository`, and the `Save` use case. All collaborators are relaxed MockK mocks; a fixed `Clock` (July 2024) pins time-dependent greeting logic.
## Key pieces
- `setup()` — constructs the ViewModel with mocked repos plus a fixed `Clock`; WHY: deterministic lineup and time behavior.
- `WHEN a rec is viewed THEN an analytics event is sent` — calls `onRecommendationViewed` and verifies `tracker.track(HomeEvents.slateArticleImpression(...))`; WHY: pins the impression contract (slate title, position, URL, recommendation id).
- `WHEN lineup contains empty slates THEN empty slates are filtered out` — emits a lineup with an empty middle slate via `MutableSharedFlow` and asserts `slatesUiState` drops it; WHY: the UI must never render empty sections.
## Junior notes
- MockK relaxed mocks return default values for unstubbed calls; `homeRepository.getLineup` must still be stubbed because the flow it returns drives the test.
- `runTest` is only needed on the flow-emission test; the pure analytics test needs no coroutine scope.
