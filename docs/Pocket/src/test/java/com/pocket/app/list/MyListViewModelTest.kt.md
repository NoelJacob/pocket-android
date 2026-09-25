# Pocket/src/test/java/com/pocket/app/list/MyListViewModelTest.kt
## What this is
Large suite (1000+ lines) for `MyListViewModel`, the Saves list screen: filter/sort chips, search, bulk edit, overflow/share/favorite actions, pull-to-refresh, empty-view and screen states, and analytics. Each test pins one chip, action, or state derivation — e.g. the archive chip sets the status filter, bulk re-add captures the selected items, search text updates the query with zero delay.
## How it fits
Guards production `com.pocket.app.list.MyListViewModel`, which coordinates `ListManager` (sort/filter/search state), `ItemRepository`, `TagRepository`, `SearchRepository`, `ModelBindingHelper`, `Notes`, and `UndoBar`. Dependencies are relaxed MockK mocks with `MutableStateFlow` doubles for `sortFilterState`; item fixtures come from `fakeItem()`.
## Key pieces
- `setup()` plus `listManagerSortFilterState` / `tagFlow` / `listManagerLoadStateFlow` doubles — controllable upstream state; WHY: every chip/state test starts from a known filter.
- Chip tests (my-list/archive/all/tagged/favorites/highlights/filter, selected-tag, not-tagged) — verify `listManager.setStatusFilter / addFilter / setTag` calls and derived `ChipState`; WHY: chips are thin views over `ListManager` state.
- Bulk-edit tests (setup, re-add, archive, delete, overflow, exit-on-any-click) — capture item lists via MockK `slot` and assert navigation/undo events via `SharedFlowTracker`; WHY: bulk actions consume and then clear the selection.
- Search tests (open, close with/without text, text-changed, done, recent-search click) — verify `isSearching`, `clearFilters`, debounced query updates; WHY: search mode resets filters and routes through `SearchRepository`.
- Item/empty/screen-state tests (favorite toggle, item click plus analytics, share/overflow navigation, `EmptyViewState`, `MyListScreenState.Loading`, edit-chip enabled, list-item binding via `ModelBindingHelper`) — verify per-row actions and state mapping.
## Junior notes
- `SharedFlowTracker` collects one-shot navigation events on `Dispatchers.Main` (provided by `BaseCoroutineTest`); assert `lastValue`, not the full list, for single-navigation tests.
- `listManagerSortFilterState.edit { copy(...) }` stages state — the ViewModel reads it reactively, so set state before acting.
- This file is over 400 lines; when editing, read the targeted test plus `setup()` rather than the whole file.
