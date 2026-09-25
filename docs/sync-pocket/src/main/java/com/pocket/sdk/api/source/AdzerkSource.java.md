# sync-pocket/src/main/java/com/pocket/sdk/api/source/AdzerkSource.java

## What this is

AdzerkSource is the Source wrapper that serves ad requests through AdzerkEndpoint, fitting ads into the same sync-source abstraction as Pocket data. It reports support only for ad syncables.

## How it fits

Pocket and discovery code call it like any other source; the endpoint does the HTTP work and AdzerkApiException carries failures.

## Key pieces

- `AdzerkSource` (class, line 35) — Adzerk's API (specifically Pocket's api with them) as a {@link Source}, supports actions and things of {@link PocketRemoteStyle#ADZERK}.
- `setUserAgent` (fun, line 46) — entry point other code calls; see callers for context.
- `syncFull` (fun, line 51) — entry point other code calls; see callers for context.
- `execute` (fun, line 99) — entry point other code calls; see callers for context.
- `fixDecisionApiRequest` (fun, line 130) — entry point other code calls; see callers for context.
- `fixDecisionApiResponse` (fun, line 148) — entry point other code calls; see callers for context.
- `fixMissingFileName` (fun, line 177) — If full path doesn't contain file name, try to fix by appending the file name.
- `isSupported` (fun, line 186) — entry point other code calls; see callers for context.

## Junior notes

- Uses Jackson JSON trees (`ObjectNode`).
- Thread-safety is explicit here (`synchronized`/`volatile`): do not call these paths from the main thread and do not add unsynchronized mutable state.

Names you will also see here: `AdzerkApiException`, `unwrapAdzerkApiException`, `AdzerkEndpoint`, `Endpoint`, `PocketRemoteStyle`, `Timestamp`.
