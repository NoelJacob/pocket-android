# Pocket/src/main/java/com/pocket/app/home/HomeViewModel.kt
## What this is
The Home tab's data owner (a Hilt ViewModel: survives rotation, constructed with repositories and helpers injected). It loads the recommendation lineup + topics + login/premium state into three observable StateFlows (observable state streams the UI collects: `uiState`, `slatesUiState`, `topicsUiState`), refreshes them on init/return/swipe with a mutex against overlap, and converts taps into one-shot `Home.Event` navigation commands. Failures show cached data with an error snackbar, or a skeleton when nothing is cached.
## How it fits
`HomeFragment` collects the three state flows into adapters and the `events` SharedFlow into navigation. Data comes from `HomeRepository` (lineup, locale-keyed), `TopicsRepository`, `UserRepository` (premium/sign-in banners), and `ItemRepository` + `Save` use-case for save/unsave. `lastRefresh` (per-user pref) plus a 12-hour staleness rule decides skeleton vs instant content.
## Key pieces
- `onInitialized` / `onUserReturned`: WHY both call `refreshData` is init subscribes the flows while return revalidates; the `refreshDataMutex` serializes the near-simultaneous calls at session start (unlocked in `invokeOnCompletion` so failures cannot jam it).
- `refreshData`: Loading state only when stale; parallel lineup+topics refresh; success stamps `lastRefresh`, failure checks both caches to pick Slates-with-snackbar vs stuck-Loading.
- `dataIsStale`: locale mismatch OR older than 12h; WHY locale-keyed is lineup content is per-language.
- `updateSlates` / `updateTopics`: map domain models to UI states, cap at 5 recs per slate, filter + report empty slates via `errorHandler` instead of rendering broken rows.
- Click handlers (`onItemClicked`, `onSaveClicked`, `onTopicClicked`, overflows, see-all, premium, sign-in): emit the matching `Home.Event`; save branches on `Save.Result.NotLoggedIn` to sign-in, unsave deletes directly.
- `onErrorRetryClicked` / `onSwipedToRefresh` / `onErrorSnackBarDismissed`: snackbar and swipe state transitions around `refreshData`.
## Junior notes
- If either refresh fails both are cancelled and the catch path runs, so a topics outage hides behind the same error bar as a lineup outage; do not assume partial success populates one list.
- State (`uiState`/`slatesUiState`/`topicsUiState`) is sticky and rotation-safe; `events` are fire-once and must be collected while resumed, which is why navigation lives there and selected-tab state lives in `uiState`.
