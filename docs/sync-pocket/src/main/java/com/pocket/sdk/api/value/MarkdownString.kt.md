# sync-pocket/src/main/java/com/pocket/sdk/api/value/MarkdownString.kt

## What this is

This is a validated value type for the sync model: it wraps a raw string/number so invalid data (bad URLs, bad colors, over-long text) is rejected at construction instead of leaking to the server. Think of it as a type-level form check used everywhere the model is built.

## How it fits

Model `Thing` classes and `*Util` helpers (ItemUtil, AccountUtil, TagUtil) hold these instead of raw strings, and V3Source serializes them into request JSON. Validation failures surface before any network call, so bad input never reaches `api.getpocket.com`.

## Key pieces

- `MarkdownString` (data class, line 8) — A string that contains markdown markup.
- `interface` (fun, line 13) — Platforms (like Android) use this to supply a platform specific way to parse the raw
- `parse` (fun, line 14) — entry point other code calls; see callers for context.
- `parsed` (fun, line 32) — The parsed string.

## Junior notes

- Value types reject bad input at construction: catch creation errors at the boundary (user input, intents) rather than deep in sync code.
