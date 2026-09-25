# Pocket/src/main/java/com/pocket/util/android/thread/TaskPoolFuture.java
## What this is
The `FutureTask` wrapper `TaskPool` puts around every `TaskRunnable`. It holds the runnable for status and cancellation, clears the reference in `done()`, and records whether cancellation came from a pool-wide bulk cancel.
For example, `TaskPool.submit()` creates one of these per task so `cancelAll()` can later flag exactly the still-active ones.

## How it fits
Created inside `TaskPool.submit()` via `newFutureTask()` (or `PriorityFutureTask` in priority pools) and tracked in `mActiveTasks`. `TaskRunnable` reaches back through it (`mFuture`) for `isCancelled()`/`get()`, and `PriorityTaskPool` extends it to add ordering.

## Key pieces
- `TaskPoolFuture(runnable)`: pairs the `FutureTask` machinery with the `TaskRunnable`. WHY it exists: gives the pool a cancellable, inspectable handle per submission.
- `bulkCancel()`: flags `mWasBulkCancelled` then calls `runnable.cancel()`. WHY it exists: distinguishes "pool tore everything down" from an individual cancel.
- `wasBulkCanceled()`: reports the bulk flag. WHY it exists: lets post-mortem or listener code tell the two cancel causes apart.
- `done()`: nulls `mRunnable` under lock. WHY it exists: releases the task reference so finished work can be garbage collected while keeping the future queryable.

## Junior notes
- Like all `FutureTask`s, `get()` blocks; call it only on background threads or via the `TaskRunnable.get()` status helper with the same caution.
- `bulkCancel()` does not interrupt the thread; a running task must poll `isCancelled()` to stop, otherwise it runs to completion.
