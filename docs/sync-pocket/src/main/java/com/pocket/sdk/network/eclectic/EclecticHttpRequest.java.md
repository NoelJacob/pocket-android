# sync-pocket/src/main/java/com/pocket/sdk/network/eclectic/EclecticHttpRequest.java

## What this is

This is part of the shared HTTP plumbing under every API call: request/response wrappers and the OkHttp client configuration. Sources program against these small types instead of touching OkHttp directly.

## How it fits

EclecticOkHttpClient implements EclecticHttp and is handed to every Source and endpoint handler from Pocket. Requests are modeled as EclecticHttpRequest with KeyValue/KeyFileValue parameters.

## Key pieces

- `EclecticHttpRequest` (interface, line 10) — A representation of an http request. Use {@link EclecticHttp#buildRequest(String)} to obtain a new instance.
- `appendQueryParameter` (fun, line 12) — entry point other code calls; see callers for context.
- `addFile` (fun, line 13) — entry point other code calls; see callers for context.
- `setHeader` (fun, line 14) — entry point other code calls; see callers for context.
- `setJson` (fun, line 15) — entry point other code calls; see callers for context.
- `clearQuery` (fun, line 16) — entry point other code calls; see callers for context.
- `getUrl` (fun, line 18) — entry point other code calls; see callers for context.
- `getParams` (fun, line 19) — entry point other code calls; see callers for context.
- `getFiles` (fun, line 20) — entry point other code calls; see callers for context.
- `getHeaders` (fun, line 21) — entry point other code calls; see callers for context.
- `getPath` (fun, line 22) — entry point other code calls; see callers for context.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.
