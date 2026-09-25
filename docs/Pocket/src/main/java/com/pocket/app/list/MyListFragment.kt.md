# Pocket/src/main/java/com/pocket/app/list/MyListFragment.kt
## What this is
This is the main Saves screen former (My List): the searchable, filterable list of everything the user saved, plus archive, bulk edit mode, notes entry, and the Continue Reading nudge. It is the busiest fragment in this chunk — list, search, notes, and snackbars live here — with all state and decisions in `MyListViewModel` and this file handling view setup plus navigation.
## How it fits
The fragment creates `MyListAdapter` (paged list + `BulkEditListItemAnimator` for edit-mode slide animations), `RecentSearchAdapter`, and the Compose `Notes` host, then collects `viewModel.navigationEvents` to navigate: reader (`goToReader` with `InitialQueueType.SavesList` so swiping moves through the list), tag/filter/add-URL/bulk-edit-overflow/item-overflow bottom sheets, add-choice popup menu, Listen panel, sign-in, and sync-error dialogs. Search keystrokes flow fragment-to-VM via `onSearchTextChanged`; back press delegates to `viewModel.onBackButtonClicked()` (closes search/edit mode first) and only finishes the activity if unhandled. `onResume` triggers the Continue Reading check, which adds a temporary card that auto-removes after 15 seconds.
## Key pieces
- `setupNavigationEventListener()` — the single `when` over `MyListNavigationEvent`; each branch shows a dialog/navigates/focuses search — the map of every exit from this screen.
- `setupListView()` — wires `MyListAdapter` + `MyListPagingScrollListener` (loads the next page near the bottom via `onScrolledNearBottom`).
- `setupUiListeners()` — bulk-edit snackbar actions, pull-to-refresh (`refreshLayout`), search text + IME search-done handling.
- `setupContinueReading()` — builds the nudge view on callback, tracks the view impression, auto-dismisses after 15s; guards `_binding?` null because the callback is async.
- `recyclerViewState` save/restore across `onDestroyView`/`onViewCreated` — preserves scroll position when leaving and returning.
## Junior notes
- `bulkEditListItemAnimator.reset()` in `onDestroyView` is required — the animator holds `ValueAnimator` listeners referencing old row bindings; skipping it leaks views.
- `onPause` clears the Continue Reading container; combined with the `onResume` check, the nudge re-evaluates every visit — don't move the check to `onViewCreated` or it fires only once.
- `newInstance()` exists for legacy manual construction; navigation-graph creation uses the no-arg constructor — keep both working.

