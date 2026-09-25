# Pocket/src/main/java/com/pocket/sdk/offline/cache/Assets.java

## What this is
The single manager for every file kept for offline use. It owns the `AssetDirectory` layout, a SQLite tracker (`AssetsDatabase`) of which files exist, who uses them (`AssetUser`), and how big the cache is, plus all read/write/register/unregister entry points. It also enforces the user's cache-size cap and exposes whether a new download is allowed right now.

## How it fits
Created once by Hilt DI (`@Singleton`). `OfflineDownloading` asks `isDownloadAuthorized()` before queueing, then `TextDownloader`/`WebDownloader`/`ImageCache` write through here; readers and `Image` loading resolve paths from here. When the cache fills, `CacheState` locks new background downloads and `CacheCleaner` trims files with no users or lowest priority. Storage failures surface as `AssetDirectoryUnavailableException` and route to `StorageErrorResolver`; logout and "clear cache" wipe through here.

## Key pieces
- `registerAssetUser()` / `unregisterAssets()` — claim and release ownership of a file. WHY: reference counting is what keeps shared CSS/images alive exactly while needed.
- `write(...)` / `written(...)` / `writeMarkup(item, view, markup, encoding)` — store bytes or per-item HTML and record size in the DB. WHY: all writes funnel here so the size tracker stays truthful.
- `isDownloadAuthorized(DownloadAuthorization)` — `ALWAYS` (user explicitly waiting) vs `ONLY_WHEN_SPACE_AVAILABLE` (background prefetch). WHY: an opened article must load even when the cache is full; prefetch must not overflow it.
- Cache-limit API (`getCacheLimitPriority()`, `getActualCacheLimit()`, `isCacheLimitSet()`, `clean()`) — user cap with a safety buffer and oldest/newest-first trims. WHY: bounds disk use without deleting the item the user is reading.
- `getAssetDirectory()` / `getAssetDirectoryQuietly()` — strict (throws when storage is gone) vs lenient (null) access to paths. WHY: background jobs skip quietly; UI paths raise and show the resolver dialog.
- `CleanListener` (`onTrimmed` / `onRemovedAll`) — callbacks for trims and wipes. WHY: `OfflineDownloading` cancels queued jobs when its files vanish.

## Junior notes
- Offline flow in plain terms: a save arrives via sync → `OfflineDownloading` checks authorization here → workers write `RIL_pages/<id>/web.html|text.html` plus shared `RIL_assets` files → the item's `offline_*` flag syncs to `OFFLINE` → the reader opens the local file. `local-vs-remote` = that cached file vs the live URL.
- Rule of thumb: check authorization before downloading, register the user before writing, never delete files yourself — only the cleaner deletes, and only user-less files.
- Cache files die on logout and count toward the user's cap; permanent app files do not belong in `Assets`.
