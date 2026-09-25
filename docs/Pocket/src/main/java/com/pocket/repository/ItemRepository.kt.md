# Pocket/src/main/java/com/pocket/repository/ItemRepository.kt
## What this is
The main read/write gateway for saved items: fetch one item (local cache and/or server), observe it as a Flow, and queue every mutation (favorite, archive, delete, save, scroll position, viewed state) as a sync-engine action. Junior trace for a save: UI calls `Save` use case → `save(url)` → `pocket.sync(null, add...)` queues an `add` action → `.await()` waits for the server round trip → later reads via `getItemOrThrow`/`getDomainItemFlow` see the synced item.
## How it fits
Injected (via Hilt DI: constructor parameters provided automatically) into ViewModels and use cases (`Save`, `GetTrack`) across Saves, Archive, reader, and share-extension flows. Reads go through `Pocket`'s local cache (`getLocal`) or remote queries (`get`); writes are fire-and-forget `pocket.sync(null, ...)` calls that update the local copy optimistically and sync in the background. This is the sync protocol in miniature: local-vs-remote means the UI reads the instant local copy while actions queue up to the server.
## Key pieces
- `ItemRepository` (interface) — the contract screens code against; `SyncEngineItemRepository` is the only implementation, so tests can fake the interface.
- `LookupStrategy` (`LocalCache`, `RemoteIfNotCached`, `ForceRemote`) — controls read cost: instant local only, local-then-network, or always network; WHY: lists scroll from cache while detail screens can force fresh data.
- `getItemOrThrow` / `getItem` — local lookup first, remote `getItemByUrl` fallback; `getItem` swallows `NoSuchElementException`/`NullPointerException` into null so callers prefer it.
- `getDomainItemFlow(url)` — live `Flow` (observable stream) of `DomainItem` via `bindLocalAsFlow`; WHY: favorite/archive taps re-emit and the UI updates without re-querying.
- Mutation group (`favorite`/`unfavorite`/`toggleFavorite`, `archive`/`unArchive` (re-add), `delete`, `markAsViewed`/`markAsNotViewed`, `setScrollPosition`, `save`) — each builds a timestamped action (`Timestamp.now()`) and queues it; batch overloads (`vararg`/`List`) send many actions in one `sync()` call.
- `getItemByShareSlug` — resolves a shared-link slug to its preview item for shared URLs that have no local entry.
## Junior notes
- Almost every mutation returns Unit and is fire-and-forget except `save()`, which `.await()`s; don't `await` the others or you serialize the UI on the network.
- `unArchive` sends a `readd` action, not an "unarchive" action; archiving vs re-adding are the two directions of the same state.
