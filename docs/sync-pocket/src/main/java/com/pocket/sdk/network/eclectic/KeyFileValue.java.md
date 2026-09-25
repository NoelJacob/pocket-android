# sync-pocket/src/main/java/com/pocket/sdk/network/eclectic/KeyFileValue.java

## What this is

This is part of the shared HTTP plumbing under every API call: request/response wrappers and the OkHttp client configuration. Sources program against these small types instead of touching OkHttp directly.

## How it fits

EclecticOkHttpClient implements EclecticHttp and is handed to every Source and endpoint handler from Pocket. Requests are modeled as EclecticHttpRequest with KeyValue/KeyFileValue parameters.

## Key pieces

- `KeyFileValue` (class, line 5) — core type of this file; callers reference it by name.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.
