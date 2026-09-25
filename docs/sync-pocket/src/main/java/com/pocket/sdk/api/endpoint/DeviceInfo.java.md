# sync-pocket/src/main/java/com/pocket/sdk/api/endpoint/DeviceInfo.java

## What this is

DeviceInfo describes the current device (model, OS version, app version) so API calls can include client metadata the server expects. It is gathered once and reused.

## How it fits

Sources attach it to request parameters built with EndpointStrings keys; AppInfo carries the companion app-side metadata.

## Key pieces

- `DeviceInfo` (class, line 11) — Information about a device connecting to v3.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.
