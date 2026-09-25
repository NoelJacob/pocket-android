# sync-gen/src/main/java/com/pocket/sync/print/java/tests/SyncTestsGenerator.java

## What this is

The runnable entry point generating the engine's test API from tests.graphqls: main() wires SyncTestsConfig through the standard generation flow for use in the base modules' unit tests. Its output (packaged partly as sync-tests-class-generator.jar plus sync-tests-usage.txt in sync-pocket-android) is what SpaceTest/MutableSpaceTest/EqualityTest import as com.pocket.sync.test.generated.

## How it fits

Driven when the test schema or emitters change; SyncTestsSpec then extends the emitted base to fill hand-written behavior. Stale test-API output shows up as mysterious contract-test failures.

## Key pieces

- `main` — test-schema generation run feeding the engine contract tests

## Junior notes

- Regenerate the test API after emitter changes before trusting contract-test results: stale output tests the old emitter.
