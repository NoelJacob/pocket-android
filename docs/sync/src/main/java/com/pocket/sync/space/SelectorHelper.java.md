# sync/src/main/java/com/pocket/sync/space/SelectorHelper.java

## What this is

A small adapter that runs Space.Selector queries against a MutableSpace: it holds a selector plus a defaultValue fallback, optionally narrows behavior for mutable selectors via mutable(), and query() executes against the right implementation or returns the default when no selector was given. The nested Select interface types the per-selector callbacks. Unsupported selector/space combinations fail fast with a clear exception instead of silently returning wrong data.

## How it fits

Generated derive code and Space internals use this to evaluate selector-based reads without each call site switching on selector types. It bridges the Space.Selector abstraction to MutableSpace.Selector specifics.

## Key pieces

- `query()` — executes the held selector against a MutableSpace or returns the default when absent
- `mutable(Select)` — registers the MutableSpace-specific evaluation for this helper
- `Select` — the callback interface for one selector evaluation

## Junior notes

- The fail-fast on unsupported selectors is intentional: catching it means the caller paired the wrong selector with the wrong space.
