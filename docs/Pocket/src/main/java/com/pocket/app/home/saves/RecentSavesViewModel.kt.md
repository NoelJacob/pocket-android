# Pocket/src/main/java/com/pocket/app/home/saves/RecentSavesViewModel.kt
## What this is
This is the ViewModel for Home's "Recent Saves" row. It streams the user's 5 most recent saves from the local repository, maps each server `Item` into a card-friendly `SaveUiState`, and exposes a three-way screen state (Loading / Saves / Empty) plus navigation events for taps.
## How it fits
`onInitialized()` (called once by Home) starts collecting `SavesRepository.getRecentSavesAsFlow(count = 5)` — a `Flow` that re-emits whenever the local save data changes, so archiving or favoriting elsewhere updates this row live. Favorite taps go straight to `ItemRepository.toggleFavorite()` (no event needed; the flow re-emit refreshes the heart). Card taps emit `Home.Event.GoToReader`, overflow taps emit `Home.Event.ShowSaveOverflow`, and "see all" emits `Home.Event.GoToMyList`, which takes the user to the full Saves list. Time-to-read text comes from `ModelBindingHelper`.
## Key pieces
- `recentSavesUiState: StateFlow<List<SaveUiState>>` — the card list the adapter collects; rebuilt wholesale in `updateSaves()` on every emission.
- `SaveUiState` — carries the raw `item` (needed for favorite/overflow actions) plus display strings; `index` is analytics position.
- `ScreenState` (`Loading` / `Saves` / `Empty`) — each carries databinding booleans (`titleVisible`, `recentSavesVisible`, `recentSavesLoadingVisible`); empty saves hide the whole section.
- `onSaveViewed()` — intentionally empty (implements the shared `Home.SavesInteractions` interface; view-tracking isn't wired here).
## Junior notes
- `mapNotNull { it.list }` silently drops null-list emissions — the row simply doesn't update rather than flashing empty; that's deliberate.
- `item.id_url?.url!!` in `onItemClicked` will crash if a save has no URL; in practice saves always have one, but don't copy this pattern for nullable network data.
- Favorite is a direct repository call, not an optimistic UI update — if the heart lags, the delay is in the repository round-trip, not this file.

