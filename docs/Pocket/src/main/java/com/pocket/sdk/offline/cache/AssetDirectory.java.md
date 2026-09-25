# Pocket/src/main/java/com/pocket/sdk/offline/cache/AssetDirectory.java

## What this is
The layout map for the on-device offline cache: given the user's chosen storage root, it computes every folder and per-item file path. On disk that looks like `RIL_offline/RIL_pages/<itemId>/web.html|text.html` for page markup, `RIL_offline/RIL_assets/` for shared images/CSS, plus `RIL_temp` (scratch) and `RIL_clean_up` (staged deletions). It creates no policy, only paths.

## How it fits
`Assets` owns one `AssetDirectory` built from the user's storage preference (internal vs SD card via `AndroidStorageLocation`). `OfflineDownloading`, `TextDownloader`, `WebDownloader`, and `AssetHandler` all ask it for `pathForWeb()`, `pathForText()`, or the assets folder when writing or rewriting links. If the storage is gone, the constructor throws `AssetDirectoryUnavailableException` and callers route through `StorageErrorResolver`.

## Key pieces
- `AssetDirectory(AndroidStorageLocation)` — derives all paths from the storage root. WHY: the root can move (SD card swap, setting change), so paths are computed, never hardcoded.
- `pathForWeb(item)` / `pathForText(item)` / `folderPathFor(item)` — per-item `web.html` / `text.html` locations keyed by `Item.idkey()`. WHY: this is what the reader opens offline.
- `getMarkupDirectory()` / `getAssetsPath()` / `getTempDirectory()` / `getCleanupPath()` — the four working areas (pages, shared assets, scratch, staged deletes). WHY: separates per-item HTML from shared files and from files awaiting deletion.
- `isOfflineCacheMissing()` / `getStorageLocation()` — health check and the underlying volume handle. WHY: detects a pulled SD card or wiped cache before any read/write.

## Junior notes
- Never persist an absolute cache path (in prefs, DB, or intents): the root changes between launches. Persist the item id and recompute via `folderPathFor()`.
- `RIL_` prefixes are historical ("Read It Later") and appear in user-visible storage; do not rename them.
