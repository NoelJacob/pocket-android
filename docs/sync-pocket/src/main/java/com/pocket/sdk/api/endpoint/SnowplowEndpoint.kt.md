# sync-pocket/src/main/java/com/pocket/sdk/api/endpoint/SnowplowEndpoint.kt

## What this is

SnowplowEndpoint posts analytics events to the Snowplow collector (`https://com-getpocket-prod1.mini.snplow.net`, schema `com.snowplowanalytics.snowplow/tp2`). It wraps the payload format the tracker expects.

## How it fits

SnowplowSource drives it from app code that logs events. In stripped fdroid builds this path is removed along with telemetry, so this endpoint documents what was there.

## Key pieces

- `SnowplowEndpoint` (object, line 7) — core type of this file; callers reference it by name.
- `Request` (class, line 34) — core type of this file; callers reference it by name.
- `SnowplowApiException` (data class, line 42) — core type of this file; callers reference it by name.
- `execute` (fun, line 14) — Executes a request to Snowplow and checks response status.
- `unwrapSnowplowApiException` (fun, line 47) — entry point other code calls; see callers for context.

## Junior notes

- Uses Jackson JSON trees (`ObjectNode`).

Names you will also see here: `EclecticHttp`.
