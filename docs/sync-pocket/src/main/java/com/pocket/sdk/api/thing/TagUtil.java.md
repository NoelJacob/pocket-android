# sync-pocket/src/main/java/com/pocket/sdk/api/thing/TagUtil.java

## What this is

This is a model helper for the sync layer: small static functions that read, build, or convert one part of the Pocket data model (items, tags, accounts, videos). It keeps model logic next to the model instead of scattered through sources.

## How it fits

Sources (V3Source, ClientApiSource) and the spec Applier/Deriver call these helpers when translating between server JSON and in-memory Things. Repositories upstream benefit without knowing the helpers exist.

## Key pieces

- `TagUtil` (class, line 17) — Tools for working with {@link Tag}s.
- `clean` (fun, line 19) — entry point other code calls; see callers for context.
- `clean` (fun, line 28) — Otherwise returns a tag with whitespace trimmed and truncated if too large.
- `cleanTagsStrs` (fun, line 42) — Any that clean returned null for are removed from the list.
- `equals` (fun, line 56) — Compares equality using the special tag rules (case insensitive)
- `equals` (fun, line 61) — Compares equality using the special tag rules (case insensitive)
- `indexOfTag` (fun, line 66) — Finds the index of the tag in the list, using the special tag comparing rules (case insensitive) or -1 if not found
- `indexOfTag` (fun, line 71) — Finds the index of the tag in the list, using the special tag comparing rules (case insensitive) or -1 if not found
- `contains` (fun, line 80) — A contains implementation using the special tag comparing rules (case insensitive)
- `addAll` (fun, line 89) — Helper for doing an addAll() between Tag and String types. It also will only add it if not already present in the collection.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.

Names you will also see here: `ReservedTag`, `Tag`.
