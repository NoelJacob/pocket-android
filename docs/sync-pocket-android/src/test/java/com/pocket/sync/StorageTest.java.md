# sync-pocket-android/src/test/java/com/pocket/sync/StorageTest.java

## What this is

The sister store-and-restore test to MutableSpaceStorageTest: store_and_restore round-trips a populated space through its store and asserts equality after restore. Together the two pin the persistence seam from both the space side and the store side, so a regression's location (snapshot writing versus restore reading) is easier to isolate.

## How it fits

Runs against scripted stores; see MutableSpaceStorageTest for the companion coverage.

## Key pieces

- `store_and_restore` — populated-space snapshot round trip

## Junior notes

- When one twin passes and the other fails, the bug is on the side the failing twin emphasizes.
