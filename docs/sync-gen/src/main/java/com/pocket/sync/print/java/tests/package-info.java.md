# sync-gen/src/main/java/com/pocket/sync/print/java/tests/package-info.java

## What this is

Declares this package as the configuration for generating from tests.graphqls: the test-API schema that backs the base module's general sync-engine unit tests. It keeps test-schema generation visibly separate from production (pocket) and learning (examples) paths.

## How it fits

Points at the test schema, SyncTestsConfig, and SyncTestsGenerator; the emitted test API is consumed by sync-pocket-android tests.

## Key pieces

- `test-schema generation wiring` — the documented link between tests.graphqls and the contract-test API

## Junior notes

- Engine-first changes start here: new engine capability gets test-schema shapes before production schema commitment.
