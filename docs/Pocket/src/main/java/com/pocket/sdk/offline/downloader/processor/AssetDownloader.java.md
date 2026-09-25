# Pocket/src/main/java/com/pocket/sdk/offline/downloader/processor/AssetDownloader.java

## What this is
The parallel fetch helper inside a web download: given each discovered `Asset`, it downloads it (or reuses the on-disk copy), recurses into stylesheets to find nested assets, and blocks until every asset has a result. It caps how many stylesheets/images one page may pull so a hostile page cannot flood the cache.

## How it fits
Owned per-page by `WebDownloader`: markup processing calls `download(asset, user, cancel)` for each image/CSS found (via `AssetHandler`), and the page waits on `await(timeout, checkIn)` before finalizing `web.html`. Actual bytes go through `Assets` and `Image`-style fetching via the injected `WebDownloader.Downloader`; worker threads come from `OfflineDownloading.ThreadPools`.

## Key pieces
- `download(asset, user, cancel)` — registers the `AssetUser`, skips fetching if already on disk (unless `refresh`), fetches otherwise, and processes stylesheets for nested refs. WHY: dedupe + recursion live in one synchronized place.
- `await(timeout, checkIn)` — blocks on a `KeyLatch` (a gate that opens per completed key) until all assets resolve; returns null on interrupt. WHY: the page cannot be finalized until every rewritten link has a known outcome.
- `Status` enum (`DOWNLOADED`, cached/skipped, failed variants) — per-asset outcome. WHY: lets the page grade itself `OFFLINE` vs `PARTIAL` vs `FAILED`.
- `maxFileSize` / `maxStylesheets` / `maxImages` caps — per-page budgets. WHY: bounds worst-case network and disk from one save.
- `results` map + `stylesheets`/`images` sets — outcome ledger and kind tracking. WHY: `WebDownloader` needs the full accounting to pick the final `Result`.

## Junior notes
- `refresh=true` forces redownload even when the file exists; default false is the fast path that marks on-disk assets `DOWNLOADED` untouched.
- `download()` is synchronized: matchers call it from the processing stream while workers complete async, so do not add unsynchronized access to `results`.
- A null return from `await()` means interrupted/cancelled — propagate it as cancel, not as asset failure.
