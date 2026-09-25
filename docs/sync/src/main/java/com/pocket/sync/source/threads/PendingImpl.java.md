# sync/src/main/java/com/pocket/sync/source/threads/PendingImpl.java

## What this is

The standard PendingResult implementation sources use internally: create one, return its PendingResult face to the caller, then complete it later with success() or fail(). proxy/publisher helpers forward completion across threads or chain results together, get() blocks for tests, abandon() cancels, and onSuccess/onFailure/onComplete register listeners. Callers only ever see the PendingResult interface so they cannot complete someone else's work.

## How it fits

Every async source operation mints one of these; the coroutine await() and Rx toObservable() adapters consume them. Returning the interface rather than this class is what keeps completion rights with the producer.

## Key pieces

- `success/fail` — the producer-only completion calls settling the result exactly once
- `proxy/publisher` — helpers forwarding completion to another result or across a thread hop
- `get/abandon` — blocking test retrieval plus cooperative cancellation
- `onSuccess/onFailure/onComplete` — consumer listener registration

## Junior notes

- Complete exactly once: double success/fail calls indicate a logic bug in the producer, so treat the second call as a defect signal.
