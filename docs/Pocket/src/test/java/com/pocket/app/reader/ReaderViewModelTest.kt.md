# Pocket/src/test/java/com/pocket/app/reader/ReaderViewModelTest.kt
## What this is
Tests for `ReaderViewModel` queue navigation. It proves next/previous buttons move through a `UrlListQueueManager` (an ordered URL list with a current index) and emit `nextClicked` / `previousClicked` analytics with the destination URL.
## How it fits
Guards production `com.pocket.app.reader.ReaderViewModel`, which hosts article/collection views and depends on `ItemRepository`, `Reader`, `ListManager`, and `DestinationHelper`. All are relaxed mocks; the queue is a real `UrlListQueueManager` over two URLs.
## Key pieces
- `setup()` — builds the ViewModel from mocks; WHY: navigation logic needs no real data.
- Next-click test — `openUrl` at index 0 then `onNextClicked()`, verifies `ReaderEvents.nextClicked("url2")`; WHY: forward move plus reporting.
- Previous-click test — `openUrl` at index 1 then `onPreviousClicked()`, verifies `ReaderEvents.previousClicked("url1")`; WHY: backward move plus reporting.
## Junior notes
- The queue index must match the opened URL or the move is a no-op; tests stage both consistently.
- Only analytics are verified here; actual content loading is the hosted article/collection ViewModels' job.
