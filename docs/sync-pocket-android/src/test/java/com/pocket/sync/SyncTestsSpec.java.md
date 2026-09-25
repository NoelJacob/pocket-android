# sync-pocket-android/src/test/java/com/pocket/sync/SyncTestsSpec.java

## What this is

The hand-written test-API spec: extends the generated SyncTestsBaseSpec with a Deriver implementing every reactive derive method (reactive_field, reactive_self, reactive_type, reactive_type_field, reactive_collection_field, all returning now()) plus an Applier whose unknown() tolerates unmapped actions. It is the reference implementation of subclass-the-base-and-fill-the-blanks that production specs copy.

## How it fits

Backs nearly every engine contract test (SpaceTest, EqualityTest, AppSourceTest all construct one); its Deriver timestamps make reactive recomputation observable. If many unrelated tests fail at once, this file changed.

## Key pieces

- `Deriver (reactive_* methods)` — hand-written derive logic stamping recomputation time so tests can observe it
- `unknown() Applier fallback` — tolerance for actions the test schema leaves unmapped

## Junior notes

- Shared by the whole suite, so edits here have blast radius: change it only to extend coverage, never to quiet a failure.
