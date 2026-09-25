# sync/src/main/java/com/pocket/sync/source/result/SyncResult.java

## What this is

The success-side counterpart to SyncException: a detailed report of a completed sync listing per-Thing and per-Action outcomes plus any resolved (server-returned) Things. Sources assemble one with the nested Builder as they work (thing(), action(), resolved() calls), then build() freezes it; readers use hasFailures/hasSuccesses/statusOf/resultOf to interpret it. Partial success is a first-class outcome, not an error.

## How it fits

Every syncFull implementation returns one (FullResultSource exists precisely for this), AppSourceTest's fake remote scripts them, and AppSource.onRemoteResult reconciles the Space from them. hasNonDiscardedFailures distinguishes real failures from intentionally dropped items.

## Key pieces

- `Builder.thing/action/resolved` — the accumulation calls recording each item's outcome and any server-returned data
- `build` — freezes the accumulated report into the immutable result handed to callers
- `hasFailures/hasNonDiscardedFailures/hasSuccesses` — summary checks so callers can branch without scanning every entry
- `statusOf/resultOf` — per-item lookups mirroring the SyncException helpers

## Junior notes

- A SyncResult can carry failures without throwing: always check hasFailures even when no exception was raised.
