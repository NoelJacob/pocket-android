# sync/src/main/java/com/pocket/sync/space/persist/MemoryStorage.java

## What this is

A DumbStorage that just keeps the snapshot in memory: restore/store/clear/release all operate on heap fields. Useless as a real persistence layer (the Space already holds this data in memory), but ideal for tests, migrations staging, and as the simplest correct example when implementing a real store. Its tiny method set is the whole point.

## How it fits

Tests and MigrationStorage use it as the throwaway or staging store; StorageTest-style round trips run against it to prove Space logic independent of SQLite. Never ship it as production persistence.

## Key pieces

- `restore/store/clear/release` — the full DumbStorage contract backed by plain heap fields

## Junior notes

- If your tests pass on this but fail on SQLite, the bug is in the SQLite store's byte handling, not in Space logic.
