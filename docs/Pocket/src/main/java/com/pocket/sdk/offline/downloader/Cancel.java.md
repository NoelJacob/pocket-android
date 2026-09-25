# Pocket/src/main/java/com/pocket/sdk/offline/downloader/Cancel.java

## What this is
A one-method polling contract for cooperative cancellation: long downloads check `isCancelled()` at safe points and abort themselves. There is no thread interruption here — the worker must ask.

## How it fits
`OfflineDownloading.cancelPredownloading()` / `cancelAll()` flags jobs; `WebDownloader`, `StreamingMarkupProcessor`, and `AssetDownloader.download()` receive a `Cancel` and stop fetching, processing, or awaiting when it flips. `cancelPredownloading()` spares HIGH-priority (user-opened) jobs while killing background prefetch.

## Key pieces
- `isCancelled()` — the single poll point. WHY: one method keeps every downloader stage (fetch, parse, await) on the same cancellation source.

## Junior notes
- Polling must happen in loops (per asset, per buffer chunk), not just at job start, or cancel appears to hang on big pages.
- Cancellation is not failure: report it as cancelled/null status, never as `FAILED`, or the retry logic will refetch work the user deliberately stopped.
