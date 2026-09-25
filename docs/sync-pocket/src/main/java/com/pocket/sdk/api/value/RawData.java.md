# sync-pocket/src/main/java/com/pocket/sdk/api/value/RawData.java

## What this is

This is a validated value type for the sync model: it wraps a raw string/number so invalid data (bad URLs, bad colors, over-long text) is rejected at construction instead of leaking to the server. Think of it as a type-level form check used everywhere the model is built.

## How it fits

Model `Thing` classes and `*Util` helpers (ItemUtil, AccountUtil, TagUtil) hold these instead of raw strings, and V3Source serializes them into request JSON. Validation failures surface before any network call, so bad input never reaches `api.getpocket.com`.

## Key pieces

- `RawData` (class, line 8) — A chunk of serialized data that should not be parsed into anything automatically.
- `equals` (fun, line 17) — entry point other code calls; see callers for context.
- `hashCode` (fun, line 28) — entry point other code calls; see callers for context.
- `toString` (fun, line 33) — entry point other code calls; see callers for context.

## Junior notes

- Uses Jackson JSON trees (`ObjectNode`).
