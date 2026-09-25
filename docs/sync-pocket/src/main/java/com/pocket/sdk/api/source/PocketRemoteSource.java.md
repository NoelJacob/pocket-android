# sync-pocket/src/main/java/com/pocket/sdk/api/source/PocketRemoteSource.java

## What this is

PocketRemoteSource is the router in front of all Pocket network traffic: it holds one V3Source (REST) and one ClientApiSource (GraphQL) and sends each sync request to whichever source supports that item's remote style. Callers never pick a transport themselves.

## How it fits

It is constructed inside Pocket with the shared EclecticHttp client and server URLs, and its results flow back through Pocket into the repositories. `PocketRemoteSourceShould.kt` documents the proven wire shapes it produces.

## Key pieces

- `PocketRemoteSource` (class, line 35) — Handles syncing to Pocket's various remote servers.
- `setCredentials` (fun, line 69) — entry point other code calls; see callers for context.
- `setMaxActions` (fun, line 78) — See {@link V3Source#setMaxActions(int)}
- `sync` (fun, line 85) — entry point other code calls; see callers for context.
- `syncFull` (fun, line 95) — entry point other code calls; see callers for context.
- `avoidBreakingSync` (fun, line 175) — entry point other code calls; see callers for context.
- `filter` (fun, line 198) — Filter the array, returning a new array, of only actions supported by this source.
- `applyResults` (fun, line 209) — Copies all action results and resolved things from a result into the builder.
- `isSupported` (fun, line 219) — entry point other code calls; see callers for context.
- `cleanupRemoteCallDetails` (fun, line 229) — Helper for some additional clean up for {@link com.pocket.sync.source.Remote.RemoteCallDetails} for pocket stuff.

## Junior notes

- Thread-safety is explicit here (`synchronized`/`volatile`): do not call these paths from the main thread and do not add unsynchronized mutable state.

Names you will also see here: `Credentials`, `PocketRemoteStyle`, `SnowplowAppId`, `ArticleView`, `EclecticHttp`, `Action`.
