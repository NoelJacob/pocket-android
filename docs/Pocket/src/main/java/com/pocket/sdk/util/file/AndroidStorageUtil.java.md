# Pocket/src/main/java/com/pocket/sdk/util/file/AndroidStorageUtil.java
## What this is
The static factory and policy helper for Pocket's storage locations (internal memory, primary external, removable SD cards). It enumerates available locations, checks read/write permission by actually probing the filesystem, and decides which options and labels the storage picker shows. It also answers whether the app itself is installed on an SD card.
## How it fits
The storage settings screen calls `getAll()` / `getExternalType()` / `getStorageLocationSummary()` to build the picker, and the offline cache layer uses the returned `AndroidStorageLocation` objects to resolve download paths. `checkExternalDirPermission()` backs every location's `checkPermissions()`. Login defaults in `BackgroundSync` consult `isInstalledOnExternalStorage()`.
## Key pieces
- `getInternal()` / `getExternal()` / `getRemovable()` / `getAll()` — factories for each location family; removable entries come from `getExternalFilesDirs()` skipping index 0 (which duplicates primary external). WHY: one call gives the picker every candidate.
- `checkExternalDirPermission(location)` — verifies Android permissions, then writes a `permissioncheck` probe file to catch filesystem-level denial. WHY: the OS can grant permission while the card itself still refuses writes, and those need different error messages.
- `PermissionStatus` (`GRANTED`, `NOT_GRANTED`, `GRANTED_BUT_FILE_SYSTEM_DENIED`, `UNAVAILABLE`) — the four outcomes of the probe. WHY: the UI distinguishes "ask the user" from "card problem".
- `ExternalType` (`INTERNAL_ONLY`, `EXTERNAL_AS_PUBLIC_INTERNAL`, `EXTERNAL_AS_REMOVABLE`) and `getExternalType()` — hides the external option when it is just emulated space on internal hardware (same free space, no sandbox benefit), unless the user picked it in the past. WHY: fewer confusing choices that gain no space.
- `getStorageLocationSummary(type, context)` — user-facing picker label, including the "internal sandboxed vs unsandboxed" wording for legacy emulated-external users. WHY: keeps old choices understandable after the policy change.
- `isInstalledOnExternalStorage(context)` — true when the APK itself lives on external storage. WHY: background sync defaults to off there because the app may vanish with the card.
## Junior notes
- `getRemovable()` appends a `Pocket` subfolder when the returned directory is not app-specific; always use the returned path rather than reconstructing one, or files land in a shared folder.
- `asRemovable(path)` rebuilds a location from a stored path string using the app context; validate `getState()` afterward since the card may be gone.
