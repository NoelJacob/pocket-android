# sync-pocket-android/src/test/java/com/pocket/sync/SyncAsserts.java

## What this is

Shared test assertion helpers: equalsState compares two Things by state equality and, on mismatch, prints a JSON-level diff (via JsonUtil.diff2 over DANGEROUS-included serialization) before failing, so failures show exactly which fields diverged instead of a bare not-equal. The fail flag toggles hard-fail versus report-only.

## How it fits

Used across engine contract tests wherever Thing equality matters; the printed diff is the first thing to read on any state-mismatch failure.

## Key pieces

- `equalsState(from, to[, fail])` — state comparison with field-level diff output on mismatch

## Junior notes

- Diffs include dangerous values deliberately for diagnosis: test output with secrets must still stay out of shared logs.
