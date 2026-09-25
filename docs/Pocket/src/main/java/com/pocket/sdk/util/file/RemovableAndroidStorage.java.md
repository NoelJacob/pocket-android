# Pocket/src/main/java/com/pocket/sdk/util/file/RemovableAndroidStorage.java
## What this is
The `AndroidStorageLocation` for a removable SD card or other secondary external volume, built around a fixed directory passed in at construction. It is ready only when that volume reports `MEDIA_MOUNTED` and the permission probe passes. Each card gets its own instance.
## How it fits
Created by `AndroidStorageUtil.getRemovable()` (one per volume from `getExternalFilesDirs()`) and by `asRemovable(path)` when restoring a saved choice. The storage picker lists these as removable options, and the offline cache writes article files to the chosen one while it stays mounted.
## Key pieces
- `RemovableAndroidStorage(context, file)` — binds the instance to Pocket's specific directory on that volume. WHY: the path is fixed per card, so state checks always refer to the same volume.
- `getPath()` — the fixed absolute path. WHY: unlike primary external, no system call can move it, so it never throws.
- `getState()` — `READY` only when the volume state for this directory is `MEDIA_MOUNTED` and permissions grant; otherwise `MISSING_PERMISSION` or `UNAVAILABLE`. WHY: cards come and go, and the UI must tell "ejected" from "needs permission".
- `checkPermissions()` — delegates to `AndroidStorageUtil.checkExternalDirPermission()`. WHY: shares the probe-file check with primary external.
## Junior notes
- The constructor is package-visible; never instantiate directly, go through `AndroidStorageUtil` so the `Pocket` subfolder fix-up is applied.
- A stored path can outlive its card; after `asRemovable()` always re-check `getState()` before reading or writing.
