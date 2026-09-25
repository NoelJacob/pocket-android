# Pocket/src/main/java/com/pocket/sdk/util/thread/PriorityTaskPool.java
## What this is
A background thread pool (a set of worker threads that run queued tasks) that always picks the highest-priority waiting task next. It is a thin priority configuration over `WakefulTaskPool`: a priority queue plus a comparator, with each submitted task wrapped in a priority-aware future. Lower-priority work like prefetching never jumps ahead of user-visible work.
## How it fits
Created wherever background work needs ordering (sync, parsing, image work) with a fixed size and pool name; tasks are `TaskRunnable`s submitted through the `TaskPool` API. The queue orders them via `PriorityFutureTaskComparator`, and the inherited wake-lock behavior from `WakefulTaskPool` keeps the device awake while tasks run.
## Key pieces
- `PriorityTaskPool(wakelocks, poolSize(s), poolName)` constructors — fixed-size or core/max/keep-alive variants, all installing a `PriorityBlockingQueue` with `PriorityFutureTaskComparator`. WHY: priority ordering is structural; callers cannot accidentally construct one with a FIFO queue.
- `newFutureTask(runnable)` — wraps each task in a `PriorityFutureTask` stamped with an incrementing creation count. WHY: the count breaks priority ties in FIFO order so same-priority tasks do not starve or reorder randomly.
## Junior notes
- Priority only orders tasks waiting in the queue; a task already running on a thread is never preempted, so keep tasks short to let priorities take effect.
- `mCreatedCount` is not synchronized; submit tasks from one thread (normally the pool's own submit path) rather than racing submissions from many threads.
