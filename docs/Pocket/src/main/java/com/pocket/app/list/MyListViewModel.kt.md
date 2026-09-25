# Pocket/src/main/java/com/pocket/app/list/MyListViewModel.kt
## What this is
This is the ViewModel for the Saves list screen (1015 lines — the largest in this chunk): it turns `ListManager`'s raw item stream into everything the list screen renders (rows, chips, filters, search, bulk-edit bar, empty states) and turns every tap into either a list operation or a one-shot navigation event. UI events flow out; navigation flows out; data flows in from repositories.
## How it fits
Data in: `ListManager.list` (paged items, `Item` or remote `SearchItem`), `sortFilterState` (status/filters/tag/search text), `loadState` (loading/error), plus tag flow, recent searches, and a notes-enabled flag. Data out: `listState` (row models with highlighted search matches, badges for highlights/tags, bold-if-unviewed, archive/edit awareness) and `uiState` (screen state, chip selection, empty-view variant, search hint, bulk-edit bar). User actions go to `ListManager` (filters, paging, search text with a debounce delay so typing doesn't spam network), `ItemRepository` (favorite, un-archive), `UndoBar` (archive/delete with undo), or `navigationEvents` (reader, sheets, Listen, sign-in, keyboard control). `savesTab` derives SAVES vs ARCHIVE from the current list status for sheets that behave differently per tab.
## Key pieces
- `updateList()` — maps each raw entry to `ListItemUiState`, resolving title/domain/excerpt through `ModelBindingHelper` with search highlighting, tag badges (including `SEARCH_MATCHING_TAG`), and `isInArchive`/`isInEditMode` flags.
- `setupListSortObserver()` — derives chip selection, active-filter chips (tag name / long-/short-read labels), empty-view variant (signed-out, tag, archive, favorite, highlights, search...), and search hint from filter state + login.
- `setupListManagerStateObserver()` / `setDefaultScreenState()` — translate cache load states into `MyListScreenState` (List / Empty / Loading / Error / Search* / Notes); empty-vs-search precedence lives here.
- Search trio — `onSearchTextChanged` (debounced via `searchDelayJob`), `onSearchDoneClicked` (records recent search), `onRecentSearchClicked` (re-applies + closes keyboard); premium-gated recent-search visibility (max 5) comes from `setupRecentSearchesListener()`.
- Bulk edit — `onItemSelectedForBulkEdit` toggles membership, `invalidateBulkEdit` refreshes row checks + snackbar count text, `onBulkArchiveClicked`/`onBulkDeleteClicked` go through `UndoBar` (undoable), `onBulkReAddClicked` un-archives directly, overflow emits a sheet event, `exitEditMode` clears selection and returns whether it consumed the action (chips/search/back all defer to it first).
- Swipe — `onItemSwipedRight/Left` both archive in Saves, un-archive in Archive (via `UndoBar` / `ItemRepository` respectively).
- `requireSignedIn {}` — gates add/favorites/tags/filter/Listen behind login, emitting `GoToSignIn` otherwise.
- `onPulledToRefresh()` — releases auto-download blocks, runs `appSync.sync` with refreshing spinner, emits `ShowSyncError` on failure.
## Junior notes
- Coroutines here (`viewModelScope`, `Flow.collect`, `debounce delay`) are background-task plumbing: collectors in `init` run for the ViewModel's lifetime — never launch these in the fragment or rotation duplicates them.
- `onAddClick` flips between opening the URL sheet directly vs showing the URL-or-note menu based on the async `notes.areEnabled()` check — before that resolves, add behaves as URL-only.
- `onNotesChipClicked`/`onAddNoteClicked` are stubs with TODOs (POCKET-10881): Notes screen state exists in `MyListScreenState.Notes` but note creation isn't wired — don't assume the button path works end to end.
- `onSaveViewed` is an empty interface method; view-tracking for list rows isn't implemented here.

