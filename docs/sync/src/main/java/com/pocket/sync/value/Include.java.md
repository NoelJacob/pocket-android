# sync/src/main/java/com/pocket/sync/value/Include.java

## What this is

A serialization-scope flag for Thing.toJson and friends: it selects which values make the cut, notably whether dangerous (sensitive, log-unsafe) values are included or redacted. add/removeAssumingPresent/contains manage the set. Normal logging and debugging exclude dangerous values; explicit export or test-diff paths include them deliberately.

## How it fits

SyncAsserts.equalsState serializes with DANGEROUS included so test diffs show the real mismatch; production logging paths omit it so tokens and personal strings never hit logcat. OpenParser and modeller redaction honor the same boundary.

## Key pieces

- `add/removeAssumingPresent/contains` — set management for the serialization scope

## Junior notes

- Including DANGEROUS anywhere near logs is a privacy incident: default to exclusion and include only in controlled test/export paths.
