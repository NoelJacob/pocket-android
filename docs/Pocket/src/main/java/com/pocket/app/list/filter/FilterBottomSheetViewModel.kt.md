# Pocket/src/main/java/com/pocket/app/list/filter/FilterBottomSheetViewModel.kt

## What this is
State holder for the sort-and-filter bottom sheet. It mirrors the global `ListManager.sortFilterState` into checkbox rows the sheet can render, and forwards every tap back to `ListManager`.

## How it fits
Created by `FilterBottomSheetFragment` via Hilt DI. `onInitialized(savesTab)` starts collecting `ListManager.sortFilterState`; each emission rebuilds `SortFilterUiState` for databinding. Row clicks (`onNewestClicked`, `onViewedClicked`, ...) call `ListManager.updateCurrentSort` / `onFilterToggled`, which refreshes the main list cache — so this ViewModel never touches the network itself.

## Key pieces
- `onInitialized(savesTab)` — WHY: subscribes to the single source of truth and derives per-row `visible`/`checked` flags from it.
- `onNewest/Oldest/Shortest/LongestClicked` — WHY: exclusive sort selection, persisted by `ListManager`.
- `onViewed/NotViewed/ShortReads/LongReadsClicked` — WHY: toggle filters; `ListManager` keeps only one active at a time.
- `SortFilterUiState` / `SortOrdersState` / `FiltersState` / `SortFilterRowState` — WHY: checkbox model per row; `visible` hides word-count options where unsupported.
- Word-count fallback — WHY: the Archive (v3) API has no shortest/longest sort, so those rows hide and active ones fall back to newest.

## Junior notes
- Labels change per tab ("Newest" vs "Newest archived first") — the sheet reads `listStatus` from state, so never hardcode these strings.
- `collect(viewModelScope)` keeps the sheet live-updating if the list state changes underneath it; cancellation is automatic when the ViewModel clears.
