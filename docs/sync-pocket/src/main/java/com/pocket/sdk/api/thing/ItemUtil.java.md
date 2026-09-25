# sync-pocket/src/main/java/com/pocket/sdk/api/thing/ItemUtil.java

## What this is

This is a model helper for the sync layer: small static functions that read, build, or convert one part of the Pocket data model (items, tags, accounts, videos). It keeps model logic next to the model instead of scattered through sources.

## How it fits

Sources (V3Source, ClientApiSource) and the spec Applier/Deriver call these helpers when translating between server JSON and in-memory Things. Repositories upstream benefit without knowing the helpers exist.

## Key pieces

- `ItemUtil` (class, line 47) — Tools for working with {@link Item}
- `getPosition` (fun, line 54) — entry point other code calls; see callers for context.
- `create` (fun, line 62) — entry point other code calls; see callers for context.
- `build` (fun, line 66) — entry point other code calls; see callers for context.
- `build` (fun, line 70) — entry point other code calls; see callers for context.
- `unhashBang` (fun, line 78) — This is used in a few places in the app.
- `isDownloadable` (fun, line 85) — entry point other code calls; see callers for context.
- `downloadables` (fun, line 94) — entry point other code calls; see callers for context.
- `isViewableOffline` (fun, line 126) — entry point other code calls; see callers for context.
- `getPercent` (fun, line 133) — entry point other code calls; see callers for context.
- `mostRecentPosition` (fun, line 138) — entry point other code calls; see callers for context.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.

Names you will also see here: `Imageness`, `ItemContentType`, `ItemSortKey`, `ItemStatus`, `ItemStatusKey`, `OfflinePreference`.
