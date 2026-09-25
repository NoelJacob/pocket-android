# Pocket/src/main/java/com/pocket/sdk/util/data/AbsDataSourceCache.java
## What this is
The shared base implementation of `DataSourceCache` that owns the list, the listeners, and the load-state machine. Subclasses only implement three hooks (`doLoadFirstPage`, `doLoadNextPage`, `doRefresh`) and report results with `setList` or `setError`. It turns every subclass into a consistent state machine for free.
## How it fits
Extended by `SyncCache`, which fills in the three hooks with sync-engine calls (`Pocket.sync()`, `source.bind()`). Screens such as the list screens interact only with the `DataSourceCache` interface, so they never see sync details. State changes fan out to registered `Listener`s, which drive adapters and loading spinners.
## Key pieces
- `loadFirstPage()` / `loadNextPage()` / `refresh()` — guard methods that ignore redundant calls (already loading, paging complete, refreshing) and set the transitional state before delegating to the `do*` hook. WHY: every subclass gets the same "don't double-load" behavior.
- `doLoadFirstPage()` / `doLoadNextPage()` / `doRefresh()` — abstract hooks where a subclass fetches data. WHY: the only sync-specific code a subclass writes.
- `setList(list, isPagingComplete)` — replaces the cached items, marks paging done or not, sets state to `LOADED`, and fires `onDataSourceChanged`. WHY: the single choke point that keeps list, state, and notifications consistent.
- `setState(state)` / `setError(error, state)` — update state (and optional error) and notify `onDataSourceStateChanged`. WHY: separates "what happened" from "what is shown".
- `isLoaded()` — true in any `LOADED_*` state, so callers can ask "do I have content to show" without enumerating states.
- `mIsRefreshPending` — set when `refresh()` arrives while already refreshing, so a second refresh runs after the first finishes. WHY: a change made mid-refresh is not silently lost.
## Junior notes
- All calls are assumed to come from the UI thread (the main thread that draws the screen). In debug builds `checkThread()` throws if you call from a background thread (a coroutine worker), so always post cache calls to the main thread.
- `getList()` returns the live internal list, not a copy; read from it but never add or remove items yourself.
