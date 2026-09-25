# sync-pocket/src/main/java/com/pocket/sdk/api/value/IdString.java

## What this is

This is a validated value type for the sync model: it wraps a raw string/number so invalid data (bad URLs, bad colors, over-long text) is rejected at construction instead of leaking to the server. Think of it as a type-level form check used everywhere the model is built.

## How it fits

Model `Thing` classes and `*Util` helpers (ItemUtil, AccountUtil, TagUtil) hold these instead of raw strings, and V3Source serializes them into request JSON. Validation failures surface before any network call, so bad input never reaches `api.getpocket.com`.

## Key pieces

- `IdString` (class, line 8) — A String that represents some kind of id or guid
- `toString` (fun, line 29) — entry point other code calls; see callers for context.
- `compareTo` (fun, line 34) — entry point other code calls; see callers for context.

## Junior notes

- Value types reject bad input at construction: catch creation errors at the boundary (user input, intents) rather than deep in sync code.
