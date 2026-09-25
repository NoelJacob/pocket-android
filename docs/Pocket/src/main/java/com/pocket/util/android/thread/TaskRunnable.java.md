# Pocket/src/main/java/com/pocket/util/android/thread/TaskRunnable.java
## What this is
The base class for all background work: subclass it, put the work in `backgroundOperation()`, and submit to a `TaskPool`. It adds cooperative cancellation, priority, status flags, optional UI-thread completion, and an `OperationListener` seam.
For example, `pool.submit(TaskRunnable.simple(() -> fetch(url)))` runs a lambda in the background, and overriding `uiOnComplete()` posts the result back to the screen.

## How it fits
Submitted via `AppThreads.submit()`/`async()`/`asyncThen()` and directly to feature pools (`ImageCache`, `AssetsDatabase`, `OfflineDownloading.Downloader`, `AppSync.SyncTask`, `ImageTask`). `TaskPool` calls `onTaskPoolSubmit()` at enqueue and `run()` executes the lifecycle; logout and bulk-cancel flows drive `cancel()`.

## Key pieces
- `backgroundOperation()`: the abstract work body. WHY it exists: the one method feature code implements; everything else is lifecycle.
- `backgroundOnSkipped()` / `backgroundOnComplete(ok, error)`: still-background hooks for cancelled and finished paths. WHY they exist: let tasks clean up or chain without touching the UI thread.
- `requiresUIResponse()` + `uiOnComplete(ok, error)`: opt-in main-thread callback. WHY it exists: safe UI updates after background work (a `coroutine`-free predecessor of modern async patterns).
- `setOperationListener(listener, returnOnUiThread)`: external observer of the outcome. WHY it exists: lets non-owners watch a task without subclassing.
- `cancel()` / `isCancelled()`: cooperative flag also reflecting pool/future cancels. WHY they exist: uniform stop signal for long or queued work.
- `PRIORITY_LOW..VERY_HIGH`, `STATUS_*` flags, `getPriority/setPriority`, `get()`, `runNow()`, `simple(...)`: priority/status model plus test and lambda conveniences. WHY they exist: ordering, inspection, synchronous execution in tests, and one-line tasks without subclasses.

## Junior notes
- `run()` is final and owned by the framework; never override it, override `backgroundOperation()` and the hooks.
- `cancel()` never interrupts; long loops must poll `isCancelled()` or they ignore cancellation.
- `get()` blocks until the future completes and throws if never submitted; never call it on the UI thread.
