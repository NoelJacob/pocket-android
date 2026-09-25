# sync-pocket/src/test/java/com/pocket/sdk/api/source/PocketRemoteSourceShould.kt

## What this is

This is part of the API transport layer: it moves one slice of Pocket data between the app and the server over HTTP. Transports own URLs, payloads, and retries; they never own UI or caching.

## How it fits

PocketRemoteSource routes work to the transport that supports each item, and results flow back through Pocket into the app repositories. Concrete URLs used here: the standard `api.getpocket.com` / `client-api.getpocket.com` hosts.

## Key pieces

- `PocketRemoteSourceShould` (class, line 24) — core type of this file; callers reference it by name.
- `dispatch` (fun, line 32) — entry point other code calls; see callers for context.
- `dispatch` (fun, line 56) — entry point other code calls; see callers for context.

## Junior notes

- Uses OkHttp HTTP client.
- Uses JUnit/Mockito/MockWebServer tests.
- Test file: run it scoped (single test class), not the whole suite; MockWebServer asserts exact request paths, so URL changes break these first.

Names you will also see here: `AppInfo`, `Credentials`, `DeviceInfo`, `PvWt`, `TrackAppOpen_1_0_0`, `POCKET_ANDROID_DEV`.
