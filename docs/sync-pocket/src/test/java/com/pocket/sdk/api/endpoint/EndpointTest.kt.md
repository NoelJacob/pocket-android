# sync-pocket/src/test/java/com/pocket/sdk/api/endpoint/EndpointTest.kt

## What this is

This is part of the API transport layer: it moves one slice of Pocket data between the app and the server over HTTP. Transports own URLs, payloads, and retries; they never own UI or caching.

## How it fits

PocketRemoteSource routes work to the transport that supports each item, and results flow back through Pocket into the app repositories. Concrete URLs used here: `https://example.com`.

## Key pieces

- `EndpointTest` (class, line 12) — core type of this file; callers reference it by name.
- `legacyHash` (fun, line 61) — The original hashing method that relied on the problematic Commons Codec library.

Concrete endpoints referenced here:

- `https://example.com`

## Junior notes

- Uses JUnit/Mockito/MockWebServer tests.
- Test file: run it scoped (single test class), not the whole suite; MockWebServer asserts exact request paths, so URL changes break these first.

Names you will also see here: `EclecticHttp`, `EclecticHttpRequest`.
