# Pocket/src/main/java/com/pocket/sdk/util/file/AndroidStorageLocation.java
## What this is
The abstract description of one place Pocket can store offline data: internal sandboxed memory, primary external storage, or a removable SD card. It defines the shared contract every location implements: a path, a readiness state, a permission check, free-space query, and path-aware equality. Obtain instances from `AndroidStorageUtil`, never construct one directly.
## How it fits
`AndroidStorageUtil.getAll()` collects one of each subclass for the storage picker and for the offline cache (`Assets` / `AssetDirectoryUnavailableException` flow). The settings screen shows `Type` names and `getStorageLocationSummary()`, while the download/cache layer calls `getPath()` and `getState()` before writing article files. `InternalAndroidStorage`, `ExternalAndroidStorage`, and `RemovableAndroidStorage` each fill in the device-specific details.
## Key pieces
- `Type` (`INTERNAL`, `EXTERNAL`, `REMOVABLE`) — which hardware family the location belongs to. WHY: names are user-facing in help info and drive picker labels.
- `State` (`READY`, `UNAVAILABLE`, `MISSING_PERMISSION`) — whether the location can be used right now. WHY: an SD card can be ejected or a permission revoked at any time, so every write path checks this first.
- `getPath()` — absolute path of Pocket's directory here; throws when unavailable. WHY: throwing (instead of returning null) forces callers to handle the ejected-card case.
- `checkPermissions()` — storage permission status for this location. WHY: distinct from `State` so the UI can show "grant permission" vs "card missing" correctly.
- `getFreeSpaceBytes()` — free bytes via `StatFs` (Android's filesystem-stats API). WHY: the app warns before downloading when space is low.
- `equalsIncludingPath(o)` — equality including the resolved path, throwing on unavailable paths instead of using `equals()`. WHY: a throwing `equals()` would break collections, so the risky comparison is explicit.
## Junior notes
- `equals()` is deprecated and intentionally identity-only; always use `equalsIncludingPath()` when comparing locations, and handle its exception.
- `getPathOfParentOfAppDirectory()` strips the app-specific tail for display; never use it for file I/O, only for showing the user which device a location is on.
