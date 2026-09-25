# sync-pocket/src/main/java/com/pocket/sdk/api/value/package-info.java

## What this is

This is a validated value type for the sync model: it wraps a raw string/number so invalid data (bad URLs, bad colors, over-long text) is rejected at construction instead of leaking to the server. Think of it as a type-level form check used everywhere the model is built.

## How it fits

Model `Thing` classes and `*Util` helpers (ItemUtil, AccountUtil, TagUtil) hold these instead of raw strings, and V3Source serializes them into request JSON. Validation failures surface before any network call, so bad input never reaches `api.getpocket.com`.

## Key pieces

- (Small file with no top-level declarations matched; read it directly — it is likely constants, aliases, or wiring.)

Classes in this package:

- `AccessToken.java`
- `DateString.java`
- `EmailString.java`
- `EscapedString.java`
- `FileField.java`
- `HexColor.java`
- `HtmlBlob.java`
- `HtmlString.java`
- `IdString.java`
- `ModellerUtil.java`
- `Password.java`
- `RawData.java`
- `Timestamp.java`
- `UrlString.java`
- `IsoDateString.kt`

## Junior notes

- Value types reject bad input at construction: catch creation errors at the boundary (user input, intents) rather than deep in sync code.
