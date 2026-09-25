# Pocket/src/main/java/com/pocket/sdk/offline/cache/DownloadAuthorization.java

## What this is
A two-value enum declaring how important a download is: `ALWAYS` (the user is explicitly waiting — fetch no matter what) versus `ONLY_WHEN_SPACE_AVAILABLE` (background prefetch — fetch only if the cache is not full). It is the priority stamp checked against the cache cap.

## How it fits
Callers pass one to `Assets.isDownloadAuthorized(...)` before writing. `OfflineDownloading.download()` (user opened an item) uses `ALWAYS`; `predownload()` paths use `ONLY_WHEN_SPACE_AVAILABLE` so bulk prefetch never overflows a capped cache. `CacheState`'s lock only blocks the latter.

## Key pieces
- `ALWAYS` — explicit user request or visible-waiting content. WHY: responsiveness beats the size cap for what is on screen.
- `ONLY_WHEN_SPACE_AVAILABLE` — speculative offline copies. WHY: prefetch is droppable work and must yield to the cap.

## Junior notes
- Picking the wrong level is a real bug: `ALWAYS` for prefetch fills the disk; `ONLY_WHEN_SPACE_AVAILABLE` for an opened article shows a spinner that never resolves. Default to `ONLY_WHEN_SPACE_AVAILABLE` unless the user is waiting.
