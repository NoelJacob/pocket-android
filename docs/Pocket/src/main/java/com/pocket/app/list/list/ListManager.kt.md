# Pocket/src/main/java/com/pocket/app/list/list/ListManager.kt

## What this is
The single source of truth for "what list am I looking at": sort order, active filter, tag, search text, and Saves-vs-Archive status, plus the loaded item list itself. Every list screen (My List, filter sheet, tag sheet, adapters) reads from here.

## How it fits
A `@Singleton` injected wherever the list is shown. `MyListViewModel` observes `sortFilterState`, `list`, and `loadState` to build screen UI; the filter/tag sheets call the `update*/set*/on*` methods, each of which updates state and calls `refreshCache()` to rebuild the backing `SyncCache` (a paging cache that syncs local DB with the server). ViewModels never query the API directly — they go through this class.

## Key pieces
- `sortFilterState: StateFlow<SortFilterState>` — WHY: observable sort/filter/tag/search/status bundle; the one thing all list UI subscribes to.
- `list` / `loadState` — WHY: the current page of items and its loading state (initial/loading/error), fed by `SyncCache` listeners.
- `refreshCache()` — WHY: picks the right data source: offline-first Saves cache, or forced-remote Archive/Search-with-Premium query.
- `loadMyList()` vs `loadRemote(status, sort)` — WHY: Saves live in the on-device DB; Archive is never cached locally so it always hits the server (v3 `Get` API, with shortest/longest falling back to newest).
- `updateCurrentSort`, `onFilterToggled`, `addFilter`, `clearFilters`, `setStatusFilter`, `setSearchText`, `setTag`, `loadNextPage` — WHY: the only mutators; each updates state then refreshes the cache. Filters are single-select: toggling the active one clears it.
- `sortPreference` — WHY: persists the chosen sort per user so it survives restarts.

## Junior notes
- `SyncCache` listeners push into `_list`/`_loadState`; setting a new `syncCache` clears the old listeners first, or you get duplicate updates.
- Remote search only kicks in for Premium users (`pocketCache.hasPremium()`); on remote error during Saves search it silently falls back to the offline list — that fallback is intentional.
