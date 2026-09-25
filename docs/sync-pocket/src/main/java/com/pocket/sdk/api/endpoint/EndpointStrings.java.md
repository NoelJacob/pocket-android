# sync-pocket/src/main/java/com/pocket/sdk/api/endpoint/EndpointStrings.java

## What this is

EndpointStrings is the shared constants file for API parameter names used across endpoints, so every call site spells keys like the server expects. One typo here would break every caller, which is why they live in one place.

## How it fits

Endpoint subclasses and sources reference these keys when building v3 and GraphQL payloads.

## Key pieces

- `EndpointStrings` (class, line 7) — Sensitive strings used by ApiRequest.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.
