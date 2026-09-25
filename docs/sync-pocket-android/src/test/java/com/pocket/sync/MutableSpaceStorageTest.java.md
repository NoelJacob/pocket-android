# sync-pocket-android/src/test/java/com/pocket/sync/MutableSpaceStorageTest.java

## What this is

Store-and-restore round trip for MutableSpace: store_and_restore persists a populated space through a DumbStorage and proves the restored space equals the original. It guards the Space-plus-storage seam (serialization, holder retention, queued actions) rather than either side alone. StorageTest covers the same seam against a different store.

## How it fits

Runs with an in-memory or scripted store; failures mean the snapshot format or the restore path drifted.

## Key pieces

- `store_and_restore` — full populated-space snapshot round trip

## Junior notes

- Round-trip tests must use realistically populated spaces: empty-space round trips prove almost nothing.
