# sync-pocket/src/main/java/com/pocket/sdk/api/endpoint/AdzerkApiException.kt

## What this is

AdzerkApiException is the ad-specific API error, extending ApiException so ad failures can be caught separately from general API failures. It preserves the ad context needed for fallback decisions.

## How it fits

AdzerkEndpoint throws it and AdzerkSource callers catch it to hide the ad slot instead of crashing.

## Key pieces

- `AdzerkApiException` (data class, line 5) — core type of this file; callers reference it by name.
- `unwrapAdzerkApiException` (fun, line 11) — entry point other code calls; see callers for context.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.
