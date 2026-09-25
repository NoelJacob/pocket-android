# Pocket/src/main/java/com/pocket/sdk/util/data/SyncCache.java
## What this is
A `DataSourceCache` backed by the sync engine: it syncs a `Thing` (the sync layer's unit of an item or collection, e.g. a user's saves) through `Pocket.sync()` and stays subscribed to it, so the list updates itself when data changes locally or on the server. Pages are `Subset` (offset/count) slices of one identity `Thing`, merged into a single display list.
## How it fits
Built via `SyncCache.from(pocket).sync(identity).display(collectionFrom).pageByOffset(...)` (or `pageByPosition` / `noPaging`), e.g. in `ListManager` for the My List screen. A save in the UI flows: UI writes to the sync `Source` -> server sync runs -> subscription callback fires -> `invalidateList()` rebuilds the merged list -> `AbsDataSourceCache.setList()` notifies the adapter. `refresh()` re-syncs the first page with the server and drops extra pages so they re-page on scroll.
## Key pieces
- `from(source)` / `Builder` / `BuilderStep2` / `BuilderStep3` / `BuilderStep4` — staged builder that forces the order: pick a source, pick the identity `Thing` to sync, describe how to extract a display list (`CollectionGet`), then pick paging. WHY: you cannot build a cache missing a piece.
- `SubsetApply` / `Subset` — how to stamp an offset/count onto a fresh identity `Thing` for each page. WHY: the server request for "items 30-60" is just a modified copy of the base request.
- `PagingStrategy` and `PagingStrategies.NextPosition` / `NextPage` — compute the next offset and decide when paging is done (short page vs. empty page). WHY: different endpoints signal "no more data" differently.
- `CollectionGet.collectionFrom(thing)` — converts one synced `Thing` into display rows. WHY: separates network shape from what the list shows.
- `doLoadFirstPage()` — clears old pages/subscriptions and `bind`s the first page identity; the first callback resolves the initial load, later callbacks just invalidate. WHY: one subscription does both initial fetch and live updates.
- `doLoadNextPage()` — binds each next page identity and appends it to `pages`. WHY: every loaded page stays subscribed, so edits to older pages refresh the list.
- `doRefresh()` — syncs the first page only (remote or local depending on its style), keeps showing old data on failure, and trims back to one page on success. WHY: refresh never blanks the screen.
- `forceRemote` — when true, binds bypass local data and hit the network. WHY: used where stale local data is unacceptable.
## Junior notes
- UI-thread assumption is inherited from `AbsDataSourceCache`, and subscription callbacks must also arrive on the UI thread (controlled by `Pocket.Config.publisher`); if they arrive on a background thread the list corrupts.
- `refresh()` on an empty cache does nothing useful since it reads `pages.get(0)`; make sure the first page loaded before offering pull-to-refresh.
- `clearData()` stops every subscription and abandons any in-flight refresh; retrying an empty first page starts fully clean, not stacked on old subscriptions.
