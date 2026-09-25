# Pocket/src/main/java/com/pocket/sdk/util/file/InternalAndroidStorage.java
## What this is
The `AndroidStorageLocation` for the app's private internal directory (`getFilesDir()`), the sandboxed space only Pocket can access. It is always available and always permission-granted, so it is the safe default and fallback when no other storage works.
## How it fits
Created by `AndroidStorageUtil.getInternal()` and always first in `getAll()`. The offline cache uses it unless the user explicitly picks another location, and it is where data goes when SD cards are ejected or permissions are missing.
## Key pieces
- `getPath()` — the absolute path of `getFilesDir()`. WHY: the one location that never throws for unavailability.
- `getState()` — always `READY`. WHY: internal storage cannot be ejected, so no availability check is needed.
- `checkPermissions()` — always `GRANTED`. WHY: no runtime permission is required for your own sandbox.
## Junior notes
- Internal space counts against the app in system settings and can trigger low-space warnings; that is why the picker and `getFreeSpaceBytes()` exist for large offline libraries.
- Files here are deleted with the app on uninstall, unlike some external files; do not use it for anything the user should keep after removing Pocket.
