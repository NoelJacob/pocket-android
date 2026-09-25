# Pocket/src/main/java/com/pocket/util/android/thread/TaskPool.java
## What this is
The app's replacement for `AsyncTask`: a pausable thread pool that only accepts `TaskRunnable` work, tracks every active task, and can bulk-cancel everything (e.g. at logout). Submissions return a `FutureTask` and record a submit timestamp for idle diagnostics.
For example, `pool.submit(TaskRunnable.simple(() -> dbHelper.close()))` runs DB teardown off the UI thread, and `pool.cancelAll()` flags all queued and running tasks as cancelled.

## How it fits
Central pool type built by `AppThreads` (`pool()`, `newWakefulPool()`, `newPriorityPool()`) and subclassed by `WakefulTaskPool` (wake locks) and `PriorityTaskPool` (priority queue). Features submit through it: `ImageCache` owns routing/resizing/downloading pools, `AssetsDatabase` funnels DB work through one, `UserManager` builds a logout pool, and `AppSync.SyncTask` runs syncs as `TaskRunnable`s.

## Key pieces
- `submit(TaskRunnable)`: wraps in `newFutureTask()`, tracks it in `mActiveTasks`, stamps `mLastSubmit`, and executes. WHY it exists: the single funnel that makes every task cancellable and observable.
- `execute(Runnable)`: wraps any stray `Runnable` via `TaskRunnable.simple()`. WHY it exists: catches accidental direct-executor use so nothing bypasses task tracking.
- `newFutureTask(runnable)`: creates the `TaskPoolFuture` wrapper, overridable. WHY it exists: lets `PriorityTaskPool` substitute `PriorityFutureTask`.
- `cancelAll()` / `cancelAllUntilEmpty()`: flag every active task via `bulkCancel()` without interrupting threads; the latter also waits for the queue to drain. WHY they exist: fast, safe teardown where cancelled-but-queued tasks skip work when they reach the front.
- `pause()` / `resume()` (inherited): suspend starting new work. WHY they exist: halt dispatch around logout or connectivity loss.
- `terminate(...)`, execution-state listeners, `hasWork()`/`isActive()`/`millisSinceLastSubmit()`: lifecycle and monitoring. WHY they exist: let owners shut pools down and diagnose stalls.

## Junior notes
- Bulk-cancelled tasks stay queued and still "run", but `TaskRunnable.run()` sees the flag and skips to `backgroundOnSkipped()`; never assume cancel means "never executes".
- `submit()` returns null once the pool is terminating; callers must null-check instead of assuming a future.
- A `FutureTask` here is a handle for "work that will complete"; `get()` blocks until done, so never call it on the UI thread.
