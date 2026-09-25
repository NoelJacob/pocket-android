# Pocket/src/main/java/com/pocket/app/reader/queue/SavesListQueueManager.kt
## What this is
This QueueManager lets the reader swipe through the user's currently open Saves list. It derives the URL list live from ListManager's observable list on every call, so list edits (archive, delete, reorder) are reflected immediately. It also triggers loading the next page of Saves as the user nears the end.
## How it fits
Built by ReaderFragment when launched with InitialQueueType.SavesList, using the shared ListManager and the tapped item's start index. Called on each swipe; calls ListManager.loadNextPage() when within LOAD_MORE_THRESHOLD of the tail so scrolling never hits a dead end while more saves exist server-side.
## Key pieces
- `urls`: computed property mapping each list row (Item or SearchItem wrapper) to its id_url — WHY navigation sees plain URLs while the list holds rich objects.
- `getNextUrl()` / `getPreviousUrl()`: bump currentIndex and return the URL at the new position (null past the ends).
- `hasNext()` / `hasPrevious()`: bounds peek via getOrNull so the UI can hide arrows at the ends.
- `LOAD_MORE_THRESHOLD (10)`: how close to the tail triggers a next-page fetch.
## Junior notes
- The URL list is recomputed per call, not cached — correct under concurrent list changes, but currentIndex can drift if items are removed ahead of the cursor; acceptable for a reading queue.
- loadNextPage is fire-and-forget pagination; the newly loaded items appear on subsequent getNextUrl calls once ListManager emits them.
