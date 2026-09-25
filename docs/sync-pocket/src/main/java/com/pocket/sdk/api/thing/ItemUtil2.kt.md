# sync-pocket/src/main/java/com/pocket/sdk/api/thing/ItemUtil2.kt

## What this is

This is a model helper for the sync layer: small static functions that read, build, or convert one part of the Pocket data model (items, tags, accounts, videos). It keeps model logic next to the model instead of scattered through sources.

## How it fits

Sources (V3Source, ClientApiSource) and the spec Applier/Deriver call these helpers when translating between server JSON and in-memory Things. Repositories upstream benefit without knowing the helpers exist.

## Key pieces

- `shouldRemove` (fun, line 8) — entry point other code calls; see callers for context.
- `filterList` (fun, line 48) — entry point other code calls; see callers for context.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.

Names you will also see here: `ItemFilterKey`, `Item`.
