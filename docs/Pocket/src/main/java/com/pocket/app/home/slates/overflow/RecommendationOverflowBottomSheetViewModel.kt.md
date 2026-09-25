# Pocket/src/main/java/com/pocket/app/home/slates/overflow/RecommendationOverflowBottomSheetViewModel.kt
## What this is
This is the tiny ViewModel behind the recommendation overflow sheet. It remembers which story the sheet is about (URL, title, analytics ID) and translates the two button taps into one-shot navigation events. It holds no UI state — the layout is static.
## How it fits
The fragment calls `onInitialized(url, title, corpusRecommendationId)` once to stash the arguments (plain `lateinit`/nullable fields). `onShareClicked()` emits `ShowShare` and `onReportThisItemClicked()` emits `ShowReport`; the fragment observes `events` (a `SharedFlow`, i.e. a one-shot event stream) and opens the share dialog or the report sheet. Hilt provides the ViewModel (`@HiltViewModel` + `@Inject`), though it currently has no injected dependencies.
## Key pieces
- `onInitialized(...)` — stores args for potential downstream use (report sheet needs URL + corpus ID).
- `Event` (`ShowShare` / `ShowReport`) — the only two things this sheet can do.
- `RecommendationOverflowInteractions` — the interface the databound XML calls, keeping layout decoupled from the class.
## Junior notes
- The stored `url`/`title`/`corpusRecommendationId` aren't read inside this ViewModel — they exist so the fragment (which keeps its own copies) and future logic have one obvious place for them; don't "clean up" without checking the fragment's copies.
- `extraBufferCapacity = 1` + `tryEmit` means a tap while the fragment isn't collecting still lands — important for fast double-taps.

