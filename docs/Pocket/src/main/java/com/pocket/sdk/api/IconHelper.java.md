# Pocket/src/main/java/com/pocket/sdk/api/IconHelper.java

## What this is
A helper that loads a server-provided `Icon` (a set of image URLs at different densities like `_1x` through `_4x`) and returns an Android drawable sized for the current screen. It picks the closest density variant, downloads it through the image pipeline, and caches the decoded drawable in memory. It exists because the same icon must look sharp on low- and high-density screens without each caller redoing the picking logic.

## How it fits
Created wherever an `Icon` thing from the sync layer needs to be shown (feed/site icons). It asks the `Image` pipeline to download the chosen URL (cached on disk via `AssetUser.forApp()`), decodes the file with `BitmapFactory`, and hands a `BitmapDrawable` to the `OnImageReadyListener` callback. From the network side it is read-only: it never writes back to the server, it only fetches an asset URL the sync already delivered.

## Key pieces
- `IconHelper(Icon data)` — wraps one synced `Icon` thing holding the per-density URLs. Exists so the density-picking state (`drawable`, `density`) lives with the data.
- `getBitmap(Context, OnImageReadyListener)` — picks the URL variant matching the screen's `densityDpi` and either returns the cached drawable immediately or starts an async `Image.build(...).cache(...)` fetch. Exists as the single entry point callers use.
- `loadBitmap(...)` — decodes the downloaded file with density scaling (`inDensity`/`inTargetDensity`) and posts the result to the UI thread. Exists to keep bitmap decoding off the main thread while drawable creation lands on it.
- `OnImageReadyListener` — one-method callback invoked on the UI thread when the drawable is ready. Exists because the fetch is asynchronous and callers (adapters, views) need a handoff point.

## Junior notes
- If the callback fires immediately, the image was already in memory; otherwise it arrives later on the UI thread. Always handle the view being recycled in between.
- The `targetDensity != density` guard drops stale decodes if the screen density changed mid-load; that is normal, not an error.
