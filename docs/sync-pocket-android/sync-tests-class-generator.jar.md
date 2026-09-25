# sync-pocket-android/sync-tests-class-generator.jar

## What this is

A checked-in prebuilt Java archive (binary, contents intentionally not documented): the packaged test-schema generator used by the sync-pocket-android test build (see registerSyncTestGenTask and SyncTestsGenerator) so test codegen runs without rebuilding sync-gen from source every time.

## How it fits

Consumed by the Gradle build to generate the test API (com.pocket.sync.test.generated) that the engine contract tests compile against; rebuilt and re-checked-in whenever the test schema or emitters change. (sha256 prefix c3ef0a9f4aad2860, 6394577 bytes.)

## Entries

- Binary artifact: role and provenance only, never contents (built from sync-gen tests config, consumed by the test build)
