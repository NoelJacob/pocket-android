# sync-pocket/src/main/java/com/pocket/sdk/api/value/HtmlString.java

## What this is

This is a validated value type for the sync model: it wraps a raw string/number so invalid data (bad URLs, bad colors, over-long text) is rejected at construction instead of leaking to the server. Think of it as a type-level form check used everywhere the model is built.

## How it fits

Model `Thing` classes and `*Util` helpers (ItemUtil, AccountUtil, TagUtil) hold these instead of raw strings, and V3Source serializes them into request JSON. Validation failures surface before any network call, so bad input never reaches `api.getpocket.com`.

## Key pieces

- `HtmlString` (class, line 9) — A String that may contain a limited set of html tags for formatting like for bold, italic, etc. TODO what subset is supported?
- `Parser` (interface, line 15) — Platforms (like Android) use this to supply a platform specific way to parse the raw
- `parse` (fun, line 16) — entry point other code calls; see callers for context.
- `parsed` (fun, line 39) — The parsed string.
- `equals` (fun, line 49) — entry point other code calls; see callers for context.
- `hashCode` (fun, line 57) — entry point other code calls; see callers for context.
- `parsed` (fun, line 62) — entry point other code calls; see callers for context.
- `toString` (fun, line 67) — entry point other code calls; see callers for context.
- `isBlank` (fun, line 71) — entry point other code calls; see callers for context.
- `toString` (fun, line 75) — entry point other code calls; see callers for context.

## Junior notes

- Value types reject bad input at construction: catch creation errors at the boundary (user input, intents) rather than deep in sync code.
