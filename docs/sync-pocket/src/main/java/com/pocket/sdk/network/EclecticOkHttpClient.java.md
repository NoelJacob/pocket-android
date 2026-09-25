# sync-pocket/src/main/java/com/pocket/sdk/network/EclecticOkHttpClient.java

## What this is

EclecticOkHttpClient is the shared OkHttp wrapper every source uses for HTTP: it configures timeouts, interceptors, and request execution in one place behind the EclecticHttp interface. One client means one place to fix networking behavior.

## How it fits

Pocket builds it once and hands it to V3Source, ClientApiSource/ClientApiHandler, and the ad/analytics sources. EclecticHttpRequest/KeyValue model the requests it sends.

## Key pieces

- `EclecticOkHttpClient` (class, line 37) — An {@link EclecticHttp} powered by OkHttp.
- `Method` (enum, line 55) — core type of this file; callers reference it by name.
- `post` (fun, line 64) — entry point other code calls; see callers for context.
- `delete` (fun, line 69) — entry point other code calls; see callers for context.
- `execute` (fun, line 74) — Executes requests that use a request body.
- `body` (fun, line 95) — entry point other code calls; see callers for context.
- `get` (fun, line 148) — entry point other code calls; see callers for context.
- `setEnabled` (fun, line 162) — entry point other code calls; see callers for context.
- `checkEnabled` (fun, line 166) — entry point other code calls; see callers for context.
- `attachHeaders` (fun, line 172) — entry point other code calls; see callers for context.
- `execute` (fun, line 182) — entry point other code calls; see callers for context.
- `used` (fun, line 189) — entry point other code calls; see callers for context.

Concrete endpoints referenced here:

- `https://github.com/square/okhttp/issues/1927`

## Junior notes

- Uses OkHttp HTTP client.

Names you will also see here: `EclecticHttp`, `EclecticHttpRequest`, `KeyFileValue`, `KeyValue`.
