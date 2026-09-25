# Pocket/src/main/java/com/pocket/util/android/thread/PriorityFutureTask.java
## What this is
A `TaskPoolFuture` that remembers its queue order and live priority so a priority queue can sort it. It snapshots the runnable's priority at submit and keeps the submission sequence number for FIFO tie-breaking.
For example, an image for the visible screen (priority HIGH) jumps ahead of a prefetch (priority LOW) submitted earlier.

## How it fits
Created by `PriorityTaskPool.newFutureTask()` (which passes `++mCreatedCount` as the order) and sorted by `PriorityFutureTaskComparator` inside a `PriorityBlockingQueue`. `TaskPool.submit()` enqueues these instead of plain futures when the pool is priority-aware.

## Key pieces
- `PriorityFutureTask(runnable, addedOrder)`: captures order and initial priority. WHY it exists: freezes the sort inputs at submit time.
- `getPriority()`: live runnable priority, falling back to the snapshot once the task finished/cancelled and `mRunnable` is null. WHY it exists: keeps the comparator working even after the wrapper clears its reference.
- `getAddedOrder()`: submission sequence. WHY it exists: the FIFO tiebreaker for equal priorities.

## Junior notes
- Priority lives on `TaskRunnable` (`PRIORITY_LOW`..`PRIORITY_VERY_HIGH`); changing it after submit affects queue order only if the queue re-sorts, so set it before submitting.
- The comparator casts queue entries to this type, so a priority queue must contain only these tasks; mixing plain Runnables throws `ClassCastException`.
