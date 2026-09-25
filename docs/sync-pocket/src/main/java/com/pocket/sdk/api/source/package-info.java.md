# sync-pocket/src/main/java/com/pocket/sdk/api/source/package-info.java

## What this is

This is part of the API transport layer: it moves one slice of Pocket data between the app and the server over HTTP. Transports own URLs, payloads, and retries; they never own UI or caching.

## How it fits

PocketRemoteSource routes work to the transport that supports each item, and results flow back through Pocket into the app repositories. Concrete URLs used here: the standard `api.getpocket.com` / `client-api.getpocket.com` hosts.

## Key pieces

- (Small file with no top-level declarations matched; read it directly — it is likely constants, aliases, or wiring.)

Classes in this package:

- `AdzerkSource.java`
- `PocketRemoteSource.java`
- `PocketResolver.java`
- `PocketSource.java`
- `V3Source.java`
- `ClientApiSource.kt`
- `SnowplowSource.kt`

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.

Names you will also see here: `Source`.
