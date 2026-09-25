# sync-pocket/src/main/java/com/pocket/sdk/api/thing/GetUtil.java

## What this is

This is a model helper for the sync layer: small static functions that read, build, or convert one part of the Pocket data model (items, tags, accounts, videos). It keeps model logic next to the model instead of scattered through sources.

## How it fits

Sources (V3Source, ClientApiSource) and the spec Applier/Deriver call these helpers when translating between server JSON and in-memory Things. Repositories upstream benefit without knowing the helpers exist.

## Key pieces

- `GetUtil` (class, line 15) — Tools for working with {@link com.pocket.sdk.api.generated.thing.Get}
- `setAllItemFlags` (fun, line 22) — Set all of the standard item field request flags.
- `list` (fun, line 41) — Converts a {@link Get#list} to a list of {@link SearchItem} which has all of the remapped values like sort_id and search_matches included.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.

Names you will also see here: `Get`, `SearchItem`, `SearchMatch`, `Safe`.
