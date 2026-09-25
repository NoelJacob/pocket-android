# Pocket/src/main/java/com/pocket/sdk/offline/OfflineDownloading.java

## What this is
The coordinator for offline reading: it decides WHAT to download (which unread items, article vs web view) and WHEN (after each sync, when a new save arrives, honoring Wi-Fi-only and retry settings). It runs each download on background thread pools (via `PriorityTaskPool`, a queue with priorities) and records the result back onto the `Item`'s `offline_text` / `offline_web` status. Think of it as the scheduler; `TextDownloader` and `WebDownloader` are the workers.

## How it fits
Created once by Hilt DI (Hilt DI = constructor parameters provided automatically; class is `@Singleton`, one instance app-wide). `AppSync` calls `predownload()` after every sync and re-arms the new-saves subscription after fetching. It asks the sync engine (`Pocket.sync(...)`) which unread items are downloadable, queues a `Downloader` per item+view, and `Downloader` in turn calls `TextDownloader` or `WebDownloader`, writes files through `Assets`, and syncs the new `OfflineStatus` back to the server. Readers later load the saved HTML via `webViewLocation()` / `articleViewLocation()`; `DownloadingService` observes the session for its notification.

## Key pieces
- `predownload()` / `predownload(item, ...)` — scans unread saves after sync and queues missing views per user preference. WHY: bulk prefetch so My List opens instantly offline.
- `download(item, view, refresh, callback)` — immediate high-priority download ignoring cache limits. WHY: the user explicitly opened an item, so it must fetch now.
- `queue(...)` + `DownloadingSession` + `ThreadPools` — dedupes by `ItemDownload`, boosts to HIGH on demand, tracks counts for the notification. WHY: prevents duplicate work and separates quick coordination from parallel asset fetching.
- `isPredownloadingAllowed(...)` — gates on login, stable network, Wi-Fi-only pref, suspend window, and `DownloadAuthorization.ONLY_WHEN_SPACE_AVAILABLE`. WHY: background prefetch must never burn mobile data or overflow a capped cache.
- `webViewLocation()` / `articleViewLocation()` — resolves where the finished `web.html` / `text.html` lives (or the single-image asset case). WHY: the reader screen needs a file path, not download logic.
- `cancelPredownloading()` / `cancelAll()` / `suspendAutoDownload()` — stop background work on network loss, pref change, cache wipe, or user cancel. WHY: conditions change mid-session.
- `Downloader` + `DownloaderCallback` + `OnDownloadStateChangedListener` — per-job runnable and its completion/session plumbing. WHY: reports per-item status while also feeding the progress notification.

## Junior notes
- Offline flow in plain terms: sync says "these unread items still need files" → this class queues jobs → workers save HTML+images under `RIL_offline` via `Assets` → item flags flip to `OFFLINE` → reader opens the local file. `local-vs-remote` here = cached HTML file vs live URL / article-view API.
- `preference()` currently returns `ARTICLE_ONLY` or nothing: the web-view prefetch setting was removed, so background downloads only fetch article text. Explicit `download()` can still fetch either.
- Logout tears down `pools` (set to null) and every gate checks `pools == null` first; always null-check before queueing after logout.
