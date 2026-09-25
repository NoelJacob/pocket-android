# Pocket/src/main/java/com/pocket/sdk/offline/package-info.java

## What this is
The package overview for offline downloading: it states the two reasons files are fetched (an image needed via the image layer, or an article/web view requested via `OfflineDownloading`) and names the core vocabulary — downloaded files are called `Asset`s and managed by `Assets`.

## How it fits
Start here before touching anything under `sdk/offline`. It points newcomers to `OfflineDownloading` (the scheduler: what/when to download), `Assets` (the file manager: where files live, who owns them, cache caps), `Asset` (one remote→local mapping), and the image layer for on-demand images. The `cache` subpackage holds storage; `downloader` + `downloader/processor` hold fetching and link rewriting.

## Key pieces
- Download-reason list (image need vs offline-view request) — the two entry points. WHY: tells you which manager to look at for a given bug.
- `Asset` / `Assets` cross-links — the shared vocabulary. WHY: every class in this package speaks in assets and asset users.

## Junior notes
- Offline files live under `RIL_offline` on the user's chosen storage and are wiped on logout; they are disposable copies, never the source of truth (the server + sync DB are).
- A save's journey is sync → `OfflineDownloading` → `TextDownloader`/`WebDownloader` → `Assets` → `RIL_pages/<id>/web.html|text.html` + `RIL_assets/` → item flag `OFFLINE` → reader opens the local file.
