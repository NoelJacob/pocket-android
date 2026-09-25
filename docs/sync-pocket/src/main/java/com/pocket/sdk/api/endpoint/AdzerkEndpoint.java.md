# sync-pocket/src/main/java/com/pocket/sdk/api/endpoint/AdzerkEndpoint.java

## What this is

AdzerkEndpoint fetches native ads from the Adzerk API as typed endpoint calls, with ad-specific error mapping in AdzerkApiException. It covers the request/response shapes also described in `adzerk.graphqls`.

## How it fits

AdzerkSource drives it when the app needs sponsored content for discovery surfaces. Failures surface as AdzerkApiException so callers can fall back to hiding the ad slot.

## Key pieces

- `AdzerkEndpoint` (class, line 12) — core type of this file; callers reference it by name.
- `execute` (fun, line 22) — Executes a request to Adzerk and parses and returns the response as a json object.
- `method` (fun, line 72) — entry point other code calls; see callers for context.
- `userAgent` (fun, line 77) — entry point other code calls; see callers for context.
- `json` (fun, line 82) — entry point other code calls; see callers for context.
- `addParam` (fun, line 87) — entry point other code calls; see callers for context.

## Junior notes

- Uses Jackson JSON trees (`ObjectNode`).

Names you will also see here: `EclecticHttp`, `EclecticHttpRequest`, `Remote`, `JsonUtil`.
