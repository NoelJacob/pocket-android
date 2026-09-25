# sync-gen/src/main/java/com/pocket/sync/print/java/tests/SyncTestsConfig.java

## What this is

The generation Config for the engine's test API (tests.graphqls): per-type modeling (fromJson/fromParser/compress/uncompress, isBlank, graphQlType) tuned to exercise engine features rather than model Pocket (its own javadoc is still TODO, but usage mirrors ExamplesConfig). The emitted test API backs SpaceTest, EqualityTest, OpenTypeTest, and friends. SyncTestsGenerator drives it; the jar plus usage file in sync-pocket-android are its packaged artifacts.

## How it fits

Feeds the test-schema generation whose output the sync-pocket-android contract tests compile against. Engine changes that need new schema shapes start with the test schema plus a config tweak here.

## Key pieces

- `test-API modeling overrides` — value mappings chosen for engine-feature coverage over realism

## Junior notes

- Extend the test schema (not Pocket's) when the engine needs new shapes: it regenerates fast and breaks nothing shippable.
