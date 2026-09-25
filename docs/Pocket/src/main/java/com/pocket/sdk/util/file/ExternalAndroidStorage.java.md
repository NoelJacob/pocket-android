# Pocket/src/main/java/com/pocket/sdk/util/file/ExternalAndroidStorage.java
## What this is
The `AndroidStorageLocation` for the device's primary external storage (the shared space returned by `getExternalFilesDir()`, which on modern phones is usually emulated internal memory, not a real card). It reports ready only when the media is mounted and the permission probe passes. It can also tell whether it is really separate hardware or just a partition of internal memory.
## How it fits
Created by `AndroidStorageUtil.getExternal()` and listed in `getAll()` for the storage picker and offline cache. Its path hosts Pocket's article files when the user picks external; `isPartionOfInternal()` drives `getExternalType()`, which decides whether external is even offered as a choice.
## Key pieces
- `getPath()` — the app-specific external files directory; throws `AssetDirectoryUnavailableException` when Android returns null. WHY: null means the storage is not currently available, and callers must handle that.
- `getState()` — `READY` only if `MEDIA_MOUNTED` and permission probe is `GRANTED`, else `MISSING_PERMISSION` or `UNAVAILABLE`. WHY: separates "grant access" from "storage gone" in the UI.
- `checkPermissions()` — delegates to `AndroidStorageUtil.checkExternalDirPermission()`. WHY: one shared probe implementation for all external-family locations.
- `isPartionOfInternal()` — true when `isExternalStorageEmulated()`. WHY: emulated external shares internal free space, so offering it gains nothing and the picker hides it.
## Junior notes
- "External" does not mean "SD card" on most devices; removable cards are `RemovableAndroidStorage`. Check `isPartionOfInternal()` before assuming separate hardware.
- `getPath()` can throw, so always check `getState()`/`isAvailable()` first on this location.
