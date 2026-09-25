# Pocket/src/main/java/com/pocket/util/java/Cancelable.java
## What this is
A tiny two-method contract for anything that can be stopped: `cancel()` requests a stop and `isCancelled()` reports whether it was stopped. It carries no threading of its own; each implementer decides what cancelling actually does.
For example, a background download task implements this so the pool can flag it and the task's own code checks `isCancelled()` and bails out early.

## How it fits
Implemented by `TaskRunnable`, the base class for all work run in `TaskPool`. `TaskPool.cancelAll()` drives cancellation by calling `cancel()` (via `TaskPoolFuture.bulkCancel()`) on every active task, and long-running task bodies poll `isCancelled()` to stop early. `AppThreads` pools and logout flows depend on this chain.

## Key pieces
- `cancel()`: requests cancellation. WHY it exists: gives callers one uniform "please stop" button regardless of task type.
- `isCancelled()`: reports cancelled state. WHY it exists: lets worker code cooperatively check and skip or abort work.

## Junior notes
- Cancelling here is cooperative, not a thread interrupt: calling `cancel()` alone does nothing unless the task checks `isCancelled()`.
- `isCancelled()` on `TaskRunnable` also returns true if the pool bulk-cancelled it or its future was cancelled, so check it rather than tracking your own flag.
