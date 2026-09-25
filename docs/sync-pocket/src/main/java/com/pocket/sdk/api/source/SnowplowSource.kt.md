# sync-pocket/src/main/java/com/pocket/sdk/api/source/SnowplowSource.kt

## What this is

SnowplowSource is the Source wrapper that routes analytics events through SnowplowEndpoint, keeping event logging behind the same source abstraction. Its shapes mirror `snowplow.graphqls`.

## How it fits

App code logs events through it rather than touching HTTP directly. Fdroid builds drop telemetry, so this source is unused there.

## Key pieces

- `Fields` (object, line 33) — core type of this file; callers reference it by name.
- `Schemas` (object, line 51) — core type of this file; callers reference it by name.
- `SnowplowSource` (class, line 205) — Source that supports sending [PocketRemoteStyle.SNOWPLOW] actions to the Snowplow collector.
- `Config` (class, line 221) — core type of this file; callers reference it by name.
- `syncFull` (fun, line 234) — entry point other code calls; see callers for context.
- `isSupported` (fun, line 286) — entry point other code calls; see callers for context.
- `payload` (fun, line 289) — entry point other code calls; see callers for context.
- `List` (fun, line 307) — entry point other code calls; see callers for context.
- `SnowplowEntity` (fun, line 318) — entry point other code calls; see callers for context.
- `Action` (fun, line 345) — entry point other code calls; see callers for context.
- `jsonObject` (fun, line 376) — entry point other code calls; see callers for context.
- `ObjectNode` (fun, line 380) — entry point other code calls; see callers for context.
- `JsonNode` (fun, line 384) — entry point other code calls; see callers for context.

Concrete endpoints referenced here:

- `https://com-getpocket-prod1.mini.snplow.net`

## Junior notes

- Uses Jackson JSON trees (`ObjectNode`).

Names you will also see here: `Credentials`, `Endpoint`, `SnowplowEndpoint`, `unwrapSnowplowApiException`, `Modeller`, `PocketRemoteStyle`.
