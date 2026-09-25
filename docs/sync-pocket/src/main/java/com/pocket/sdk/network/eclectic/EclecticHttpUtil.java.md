# sync-pocket/src/main/java/com/pocket/sdk/network/eclectic/EclecticHttpUtil.java

## What this is

This is part of the shared HTTP plumbing under every API call: request/response wrappers and the OkHttp client configuration. Sources program against these small types instead of touching OkHttp directly.

## How it fits

EclecticOkHttpClient implements EclecticHttp and is handed to every Source and endpoint handler from Pocket. Requests are modeled as EclecticHttpRequest with KeyValue/KeyFileValue parameters.

## Key pieces

- `EclecticHttpUtil` (class, line 13) — core type of this file; callers reference it by name.
- `getString` (fun, line 21) — Convenience method for obtaining the content of a url as a string.
- `postString` (fun, line 40) — Convenience method for obtaining the content of a url as a string.
- `getContentType` (fun, line 57) — Helper for extracting the mimeType and encoding of a response.
- `getMimeType` (fun, line 73) — Helper for extracting the mimeType from a response.
- `getContentLength` (fun, line 82) — Helper for extracting content length as a byte length

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.
