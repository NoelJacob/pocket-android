# sync-gen/src/main/java/com/pocket/sync/print/java/ActionApplierGenerator.java

## What this is

Emits the per-API Applier helper: one method per action, carrying that action's docs and Java types, so concrete specs get a typed starting point for hand-writing action effects. classname() computes the emitted class name. It answers what-can-be-applied without dictating the product logic, which stays hand-written in the Spec subclass.

## How it fits

Runs inside Generator.generate() after actions are resolved; the emitted helper is subclassed/extended by hand-written spec code (SyncTestsApplier in tests is the lived-in example). See ActionGenerator for the Action classes themselves.

## Key pieces

- `classname` — derives the emitted Applier helper's class name from the API definition

## Junior notes

- Generated appliers carry signatures and docs, never product decisions: the effect of archive or favorite is always hand-written.
