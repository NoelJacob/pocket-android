# Pocket/src/main/java/com/pocket/app/reader/queue/EmptyQueueManager.kt
## What this is
This is the "no queue" implementation of QueueManager used when the reader has no previous/next article to offer. Every method returns null or false, so swipe/next/previous controls stay hidden or disabled. It exists so callers never need a null check for the queue.
## How it fits
Chosen when ReaderFragment opens with InitialQueueType.Empty (for example a single shared link with no list behind it). The reader calls hasNext/hasPrevious to decide whether to show navigation affordances and getNextUrl/getPreviousUrl when the user tries to move — all safely no-op here.
## Key pieces
- `EmptyQueueManager`: the whole class — a null-object that satisfies the QueueManager contract with empty answers.
## Junior notes
- Null-object pattern: instead of passing a nullable queue and checking everywhere, pass an object whose methods do nothing sensible.
