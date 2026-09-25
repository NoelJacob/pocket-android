# Pocket/src/main/java/com/pocket/sdk/util/data/DataSourceCache.java
## What this is
The interface for a paged, observable list cache used by list screens. It describes loading the first page, loading more pages, and refreshing, plus reading the current items, load state, and last error. List UI observes it through a listener instead of polling.
## How it fits
Implemented by `AbsDataSourceCache` (shared state-machine logic) and concretely by `SyncCache`, which backs it with the sync engine (local-vs-remote data layer). Producers like `ListManager` create a `SyncCache` and the UI (adapters/fragments) calls `loadFirstPage` / `loadNextPage` / `refresh` and reacts to `Listener` callbacks.
## Key pieces
- `LoadState` — the lifecycle of the data: `INITIAL`, `INITIAL_LOADING`, `INITIAL_ERROR`, `LOADED`, plus `LOADED_APPENDING` / `LOADED_APPEND_ERROR` for pagination and `LOADED_REFRESHING` / `LOADED_REFRESH_ERROR` for refreshes. WHY: the UI shows spinners, error rows, or content from this single value.
- `loadFirstPage()` / `loadNextPage()` / `refresh()` — async operations; each is a no-op when there is nothing to do (already loaded, already loading, paging complete). WHY: screens can call them freely from scroll or pull-to-refresh without guards.
- `size()` / `get(position)` / `getList()` / `isPagingComplete()` — read access to the currently loaded items. WHY: adapters bind rows from these.
- `getState()` / `getError()` — current state plus the last error, whose `retry()` re-runs the failed operation. WHY: error rows can offer a retry button without knowing which operation failed.
- `Listener` (`onDataSourceChanged`, `onDataSourceStateChanged`) — observer callbacks for content and state changes. WHY: this is how the list knows to redraw.
## Junior notes
- This is plain Java, not `StateFlow` (an observable state stream): you register with `addListener`, there is no automatic lifecycle handling, so unregister or drop the cache when the screen goes away.
- `get(position)` throws if out of range; always guard with `size()` because refresh resets the list back to one page.
