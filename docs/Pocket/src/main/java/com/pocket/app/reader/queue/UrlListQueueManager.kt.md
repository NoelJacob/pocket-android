# Pocket/src/main/java/com/pocket/app/reader/queue/UrlListQueueManager.kt
## What this is
This is the fixed-list QueueManager: it navigates a snapshot list of URLs starting at a given index. Unlike SavesListQueueManager it never talks to the network or observes list changes — what you pass in is what you can swipe through. It fits flows like search results or a shared batch of links.
## How it fits
Constructed with a concrete List<String> and startingIndex by whatever launches the reader with a known set. The reader calls hasNext/hasPrevious for affordances and getNextUrl/getPreviousUrl to move.
## Key pieces
- `urls` / `currentIndex`: the immutable snapshot and the mutable cursor into it.
- `getNextUrl()` / `getPreviousUrl()`: advance the cursor and return the URL, or null past either end.
- `hasNext()` / `hasPrevious()`: null-safe bounds checks via getOrNull.
## Junior notes
- Because the list is a snapshot, items archived or deleted mid-read still appear in the queue — fine for short-lived flows, wrong choice for the main Saves list (use SavesListQueueManager there).
