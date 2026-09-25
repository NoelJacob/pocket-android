# Pocket/src/main/java/com/pocket/app/reader/queue/QueueManager.kt
## What this is
This is the interface for previous/next article navigation inside the reader. It answers two questions: is there a neighbor (hasPrevious/hasNext) and what URL is it (getPreviousUrl/getNextUrl, which also advance the position). Default implementations return null/false so simple cases like EmptyQueueManager need no code.
## How it fits
ReaderFragment (or its ViewModel) holds a QueueManager built from InitialQueueType and calls it on swipe or next/previous taps. Concrete implementations are SavesListQueueManager (navigates the live Saves list) and UrlListQueueManager (navigates a fixed URL list).
## Key pieces
- `getPreviousUrl()` / `getNextUrl()`: move the cursor and return the neighbor URL, or null at the ends.
- `hasPrevious()` / `hasNext()`: peek without moving, used to show or hide navigation UI.
## Junior notes
- Interface default methods mean implementors only override what they support — but note getNextUrl both mutates position and returns a value, so call it once per navigation, not once to check and once to go.
