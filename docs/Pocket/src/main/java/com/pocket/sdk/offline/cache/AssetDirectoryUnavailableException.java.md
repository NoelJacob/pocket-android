# Pocket/src/main/java/com/pocket/sdk/offline/cache/AssetDirectoryUnavailableException.java

## What this is
A checked exception (a normal `Exception` subclass the compiler forces callers to handle) meaning the offline storage folder is missing, corrupt, or on unavailable removable storage. It signals "stop file work and ask the user", not a bug.

## How it fits
Thrown when building `AssetDirectory` or resolving per-item paths in `Assets.getAssetDirectory()` / `OfflineDownloading.webViewLocation()`. Callers catch it and invoke `Assets.checkForStorageIssues(activity, callback)` which drives `StorageErrorResolver` dialogs (re-grant permission, pick storage, free space, or reset the cache).

## Key pieces
- `AssetDirectoryUnavailableException(String)` — message-only constructor. WHY: the message names which directory failed; resolution is delegated to the resolver, not encoded here.

## Junior notes
- This is an expected user-environment failure (SD card pulled, permission revoked), so always route it to the resolver UI rather than just logging it.
- Methods with `Quietly` in their name (e.g. `getAssetDirectoryQuietly()`) swallow this and return null; check for null instead of catching.
