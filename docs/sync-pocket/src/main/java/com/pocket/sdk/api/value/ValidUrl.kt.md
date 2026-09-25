# sync-pocket/src/main/java/com/pocket/sdk/api/value/ValidUrl.kt

## What this is

This is a validated value type for the sync model: it wraps a raw string/number so invalid data (bad URLs, bad colors, over-long text) is rejected at construction instead of leaking to the server. Think of it as a type-level form check used everywhere the model is built.

## How it fits

Model `Thing` classes and `*Util` helpers (ItemUtil, AccountUtil, TagUtil) hold these instead of raw strings, and V3Source serializes them into request JSON. Validation failures surface before any network call, so bad input never reaches `api.getpocket.com`.

## Key pieces

- `ValidUrl` (data class, line 7) — Value that conforms to the standard URL format as specified in RFC3986:
- `String` (fun, line 17) — entry point other code calls; see callers for context.

Concrete endpoints referenced here:

- `https://www.ietf.org/rfc/rfc3986.txt.`

## Junior notes

- Value types reject bad input at construction: catch creation errors at the boundary (user input, intents) rather than deep in sync code.
