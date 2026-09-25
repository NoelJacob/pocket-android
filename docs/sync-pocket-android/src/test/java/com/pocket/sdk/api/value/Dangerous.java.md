# sync-pocket-android/src/test/java/com/pocket/sdk/api/value/Dangerous.java

## What this is

A test-only sensitive-string wrapper: Dangerous marks values that must never appear in logs (toString returns REDACTED, an empty string, while value holds the real data, with equals/hashCode over the real value). Redaction-on-print is the last line of defense when a secret accidentally reaches a log statement.

## How it fits

Used by OpenDangerousUsages fixtures and redaction tests to prove dangerous values survive parsing but never leak into string output. Production code uses the same pattern for real secrets.

## Key pieces

- `REDACTED` — the safe stand-in emitted by toString instead of the real value
- `value + equals/hashCode` — real-data semantics for logic, redacted rendering for humans

## Junior notes

- If a log ever shows a real secret, the wrapper worked but the call site bypassed it: log the wrapper, never .value.
