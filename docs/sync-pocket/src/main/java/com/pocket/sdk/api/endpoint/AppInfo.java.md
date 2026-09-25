# sync-pocket/src/main/java/com/pocket/sdk/api/endpoint/AppInfo.java

## What this is

AppInfo describes the calling app (app id, version) sent as metadata with API requests. It pairs with DeviceInfo to identify the client to the server.

## How it fits

Sources include it when building request payloads; the server uses it for whitelisting API keys as native clients.

## Key pieces

- `AppInfo` (class, line 7) — Information about the app connecting to the v3 api. For the most part you will want to check with the data and server teams for what values you should use here.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.
