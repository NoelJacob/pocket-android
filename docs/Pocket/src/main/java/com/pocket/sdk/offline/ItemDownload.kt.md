# Pocket/src/main/java/com/pocket/sdk/offline/ItemDownload.kt

## What this is
A tiny data holder (Kotlin `data class` = auto-equals/hash holder) pairing a saved `Item` with the view to download. `PositionType` here means which rendering of the item: article view (cleaned text) or web view (original page). It is the queue key for one download job.

## How it fits
`OfflineDownloading.queue()` builds an `ItemDownload` for each pending view and hands it to an inner `Downloader` task on the coordinator thread pool. The download session map (`session.downloading`) is keyed by this object, so duplicate requests for the same item+view collapse into one job unless `refresh` is true.

## Key pieces
- `item: Item` — the synced save whose content is fetched. WHY: carries the URL, ids, and offline status flags the downloaders need.
- `view: PositionType` — `ARTICLE` or `WEB`; which file (`text.html` vs `web.html`) this job produces. WHY: one item can need two separate downloads.

## Junior notes
- Kotlin `data class` equality is structural, which is exactly what a map key needs; two requests for the same item+view compare equal and dedupe.
- `PositionType` is a generated API enum shared with the sync layer, not a UI concept. Do not confuse it with screen position.
