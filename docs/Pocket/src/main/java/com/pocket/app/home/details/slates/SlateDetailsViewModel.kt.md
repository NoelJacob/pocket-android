# Pocket/src/main/java/com/pocket/app/home/details/slates/SlateDetailsViewModel.kt
## What this is
This is the ViewModel behind the slate-details screen. It shows the stories of one Home slate, identified by its position in the lineup. It does no networking of its own — it reuses the already-loaded Home lineup — so it appears instantly and has no error state.
## How it fits
Created by Hilt (note `@HiltViewModel` + `@Inject constructor`: dependencies are provided automatically). `SlateDetailsFragment` calls `onInitialized(index)` once; the ViewModel then collects `HomeRepository.getLineup(localeString)` (a `Flow`, i.e. a stream that re-emits whenever the lineup changes) and maps the slate at that index into a title plus `RecommendationUiState` list. Save/overflow/tap handling is inherited from `DetailsViewModel`; taps emit `GoToReader`.
## Key pieces
- `onInitialized(index)` — starts the collector and flips `screenState` to `Recommendations`; called once from the fragment.
- `setupSlateCollector(index)` — `lineup.getOrNull(index)` guards against the lineup shrinking; a missing slate yields an empty title/list rather than a crash.
- `onErrorRetryClicked()` — intentional no-op: slate data comes from the cached lineup flow, so a network-error retry screen can never appear here.
- `onItemClicked(...)` — emits `Event.GoToReader(url)`; `positionInList`/`corpusRecommendationId` are accepted for the shared interface but only the URL is needed downstream.
## Junior notes
- `locale.toString()` is captured once at construction; changing the device language requires recreating the ViewModel to take effect.
- Because it collects the lineup flow continuously, edits made elsewhere (e.g. saving a story on Home) update this screen's save badges automatically — no manual refresh needed.

