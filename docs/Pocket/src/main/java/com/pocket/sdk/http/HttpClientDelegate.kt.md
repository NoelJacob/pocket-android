# Pocket/src/main/java/com/pocket/sdk/http/HttpClientDelegate.kt

## What this is
Owns the app's single shared HTTP client used for all network access. It lazily builds one `OkHttpClient` (short connect timeout, long read timeout) wrapped as an `EclecticHttp` client, adds request logging on internal builds, and tears the client down on logout so no authenticated request outlives the session. Every sync, fetch, and API call goes through the client it hands out.

## How it fits
Singleton injected with the build mode, `PocketServer`, `NetworkStatus`, and prefs; anything needing the network calls `getClient()`. The logging level persists in the `dcfig_lg_http` preference and is honored only on internal builds. On logout (`onLogoutStarted`), it releases pending requests and drops the client so the next login builds a fresh one. Tracing a save: UI queues the action, the sync layer borrows this client to POST to `PocketServer.api()`.

## Key pieces
- `getClient()` — lazily creates and returns the shared client. Exists so all callers share connection pooling and configuration instead of each building their own.
- `addLogging()` / `LoggingInterceptor` — attaches body-level logging on internal builds, full body only for Pocket API hosts at `API` level and everything at `EVERYTHING` level. Exists so QA can inspect traffic without flooding logs with third-party bodies.
- `getLoggingLevel()` / `setLoggingLevel(...)` — reads/writes the persisted logging preference, forced to `NONE` on production. Exists as the internal settings toggle behind the interceptor.
- `status()` — exposes the injected `NetworkStatus`. Exists so callers with only the delegate can still check connectivity.
- `onLogoutStarted()` — releases the client on logout (`stopModifyingUserData`) and nulls it on restart. Exists to cancel in-flight authenticated work and prevent cross-user reuse.

## Junior notes
- Never cache the returned client across logout; always call `getClient()` fresh, since logout nulls it.
- Production builds never log bodies regardless of the preference; if you need traffic inspection, use an internal build and the `dcfig_lg_http` setting.
