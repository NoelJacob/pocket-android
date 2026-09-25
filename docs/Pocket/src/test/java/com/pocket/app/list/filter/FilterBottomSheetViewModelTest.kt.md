# Pocket/src/test/java/com/pocket/app/list/filter/FilterBottomSheetViewModelTest.kt
## What this is
Tests for `FilterBottomSheetViewModel`, the sort-and-filter bottom sheet over Saves. It proves tapping a sort button calls the matching `ListManager` sort function with correct checked state, tapping filters toggles them, and archive mode hides shortest/longest-read sorts (falling back to newest when one was active).
## How it fits
Guards production `FilterBottomSheetViewModel`, which is a thin view over `ListManager.sortFilterState` (a `MutableStateFlow` double here, i.e. an observable state stream). `ListManager`, `StringLoader`, and `Tracker` are relaxed MockK mocks.
## Key pieces
- `listManagerSortFilterState` double plus `setup()` stubbing `listManager.sortFilterState`; WHY: the sheet mirrors and mutates this state.
- Sort-click test — verifies the right `ListManager` sort setter plus checked-state mapping; WHY: each sort row maps to one manager call.
- Filter-click test — verifies add/remove filter calls plus checked state; WHY: filters are multi-select toggles.
- Archive tests — assert shortest/longest-read rows are hidden in archive and fall back to `NEWEST`; WHY: read-time sorts are meaningless for archived items.
## Junior notes
- State is read via `subject.uiState.value`; mutate through the ViewModel's click handlers, never by editing state directly.
- `ItemSortKey` / `ItemFilterKey` are generated API enums; the test pins which enum each button maps to.
