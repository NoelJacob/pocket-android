# sync-pocket/src/main/java/com/pocket/sdk/api/source/PocketResolver.java

## What this is

PocketResolver decides where a sync request should go (local cache vs remote source) and in what order. It keeps the sync path deterministic when several sources could serve the same thing.

## How it fits

It is used inside the Pocket sync pipeline before a Source is invoked, so repositories get consistent read-then-write behavior without knowing the routing rules.

## Key pieces

- `PocketResolver` (class, line 13) — Pocket's {@link Resolver} implementation.
- `resolve` (fun, line 16) — entry point other code calls; see callers for context.
- `reduce` (fun, line 34) — entry point other code calls; see callers for context.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.

Names you will also see here: `Guid`, `Item`, `PocketShare`, `Space`, `Resolver`, `Thing`.
