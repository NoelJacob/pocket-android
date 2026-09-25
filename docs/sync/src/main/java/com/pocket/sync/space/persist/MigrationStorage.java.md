# sync/src/main/java/com/pocket/sync/space/persist/MigrationStorage.java

## What this is

A DumbStorage wrapper that runs a one-time data migration on first restore, then delegates to the real store forever after. The nested Type/Source/Access types describe the supported migration kinds (for example remapping idkeys when server identity schemes change, see SqliteBinaryStorage.migrateIdkeys). First restore migrates-then-loads; later restores load straight from the main store, so the migration cost is paid exactly once.

## How it fits

Apps wrap their real store (usually SqliteBinaryStorage) in this when a schema or identity change ships, so existing installs upgrade cleanly while fresh installs skip the work. restore/store/clear/release mirror DumbStorage outward.

## Key pieces

- `Type/Source/Access` — the migration-kind vocabulary selecting what transformation runs
- `first-restore semantics` — migrate once, then behave exactly like the wrapped store

## Junior notes

- Migrations run before any Space reads: a crashing migration blocks startup, so keep them small, idempotent, and well logged.
