# sync/src/main/java/com/pocket/sync/source/threads/ThreadPublisher.java

## What this is

A Publisher with its own dedicated thread: published callbacks queue up and run one at a time, in order received, on that single owned thread. The OwnedThread nested type manages the thread's life. This gives deterministic serialized delivery without callers coordinating locks, at the cost of one permanent thread.

## How it fits

Sources use this when callback ordering matters more than parallelism (for example serializing subscriber notifications). publish is the entry point; everything else is thread housekeeping.

## Key pieces

- `publish` — enqueues one callback for in-order, one-at-a-time execution on the owned thread
- `OwnedThread` — the managed worker thread and its lifecycle

## Junior notes

- One thread means one slow callback stalls everything behind it: keep published work short or pick a pool instead.
