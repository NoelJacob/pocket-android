# sync-pocket-android/src/test/java/com/pocket/sync/OpenTypeTest.java

## What this is

Open-type parsing proofs (json, compression, dangerous): interface and variety fields parse from JSON, survive binary compression round trips, and honor dangerous-value redaction. Open types (fields holding many possible Thing kinds, dispatched via _type tags) are the highest-risk parsing path, so all three tests attack it from different angles with no special JsonConfig.

## How it fits

Exercises OpenParser plus CompressGenerator output on OpenUsages/OpenDangerousUsages fixtures; behavior must match the tree and streaming parsers identically.

## Key pieces

- `json` — mixed-kind payloads resolving to the right concrete Things
- `compression` — open-typed Things surviving binary persist/restore
- `dangerous` — sensitive values inside open types staying redacted

## Junior notes

- New open-type members must extend these fixtures: an untested member is an untested dispatch branch in production.
