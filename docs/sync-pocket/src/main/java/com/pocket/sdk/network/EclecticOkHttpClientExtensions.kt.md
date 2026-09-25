# sync-pocket/src/main/java/com/pocket/sdk/network/EclecticOkHttpClientExtensions.kt

## What this is

This is part of the shared HTTP plumbing under every API call: request/response wrappers and the OkHttp client configuration. Sources program against these small types instead of touching OkHttp directly.

## How it fits

EclecticOkHttpClient implements EclecticHttp and is handed to every Source and endpoint handler from Pocket. Requests are modeled as EclecticHttpRequest with KeyValue/KeyFileValue parameters.

## Key pieces

- `OkHttpClient` (fun, line 5) — entry point other code calls; see callers for context.

## Junior notes

- Uses OkHttp HTTP client.
