# sync-pocket-android/src/test/java/com/pocket/sync/space/DeepCollectionBuilder.java

## What this is

Fixture builder for DeepCollectionsTest instances: create() plus per-depth helpers (createDepth0/createDepth1 with val/obj/ref-list/ref-map/val-list/val-map/obj-list/obj-map setup, two elements per collection) synthesize a deeply nested JSON graph exercising every nesting combination. SpaceTest.collectionsNest() consumes it. Quick-and-dirty by its own admission, but the nesting matrix it builds would be unreadable as a static file.

## How it fits

Supports SpaceTest nesting coverage; deepcollections.json is the static counterpart for parser-level tests.

## Key pieces

- `create` — top-level assembly of the full nested fixture graph
- `per-depth/per-kind setup helpers` — systematic coverage of value, object, reference, list, and map nesting at each depth

## Junior notes

- Generated builders must round-trip whatever this synthesizes: new nesting kinds here require parser support first.
