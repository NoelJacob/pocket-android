# sync/src/main/java/com/pocket/sync/space/persist/DumbStorage.java

## What this is

The persistence seam for Spaces: deliberately logic-free read/write of the Space's data (Things, Holders, queued Actions, invalidation marks) with some blocking and some async methods. Dumb is a compliment here: all smarts (merging, resolving, deriving) stay in the Space, so stores only need to bytes-in/bytes-out reliably. The ThingCallback/HolderCallback/ActionCallback/InvalidCallback/WriteSuccess types shape the restore and write conversations.

## How it fits

MutableSpace takes an optional DumbStorage to survive restarts; SqliteBinaryStorage is the Android implementation and MemoryStorage the test/example one, with MigrationStorage wrapping upgrades. StorageTest proves store-and-restore round trips.

## Key pieces

- `restore callbacks` — per-data-kind delivery (things, holders, actions, invalidations) during startup load
- `store/clear/release` — write-back, wipe, and teardown for the persisted snapshot
- `WriteSuccess` — the async write acknowledgment so the Space knows the snapshot landed

## Junior notes

- Keep stores dumb on purpose: any merge or dedup logic smuggled in here will fight the Space and corrupt restores.
