# sync-pocket-android/src/main/java/com/pocket/sdk/api/endpoint/AndroidDeviceInfo.java

## What this is

The Android DeviceInfo: device metadata (model, OS, locale via localeFrom) the SDK attaches to API calls that need device context (analytics-adjacent endpoints, capability checks). It implements the engine-agnostic DeviceInfo contract with Android APIs, keeping platform reads out of shared code. Small surface, but every request needing device context flows through it.

## How it fits

Supplied during AndroidPocket construction; endpoints read it when building requests. Platform-specific by design: other platforms provide their own DeviceInfo.

## Key pieces

- `localeFrom` — locale extraction honoring Android's locale APIs across versions

## Junior notes

- Device info is fingerprint-adjacent: only attach what the endpoint genuinely needs, and never log it casually.
