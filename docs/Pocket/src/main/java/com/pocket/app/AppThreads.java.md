# Pocket/src/main/java/com/pocket/app/AppThreads.java
## What this is
The app's threading toolbox, as a Hilt singleton. It owns the main-thread `Handler` (Android's mechanism for posting work to the UI thread), a shared general-purpose background pool that auto-stops at logout, and factories for dedicated pools. Most background work goes through `async(...)`; most UI callbacks go through `runOrPostOnUiThread(...)`.
## How it fits
Used pervasively: `UserManager.authenticate`/`logout`, sync engine thread pools (via `PocketSingleton.WakefulPools`), upgrade tasks, and any feature needing "do X off the UI thread, then Y on it". Its logout policy terminates the shared pool so no logged-in user's work bleeds into the next session; `AppScope` is the coroutine equivalent for suspend-style work.
## Key pieces
- `postOnUiThread` / `runOrPostOnUiThread` / `runOffUiThread`: WHY three variants is to avoid redundant hops: run immediately when already on the right thread, post/submit only when crossing threads.
- `async(Runnable)`: fire-and-forget on the shared pool; uncaught exceptions are only logged, so this is for work where failure needs no reaction.
- `async(SimpleTask, OnError)`: same but routes throwables to `onError` (still on the worker thread); for tasks that must report failure to someone.
- `asyncThen(SimpleTask, UiThreadResponse)`: runs background work then a UI-thread completion callback with success/crash; the pre-coroutine equivalent of launching a background coroutine and returning to the main thread.
- `pool()`: lazily builds a `WakefulTaskPool` (holds a wake lock while tasks run so the device does not sleep mid-write) sized 5 core / 128 max.
- `newWakefulPool` / `newPriorityPool` (+ custom-setting overloads): dedicated pools with idle-thread timeouts; NOT logout-managed, so owners needing cleanup must add their own `AppLifecycle.LogoutPolicy`.
- `getLogoutPolicy`: terminates the shared pool (20s grace) on stop, rebuilds lazily on restart; `UserManager` runs this near-last since logout itself submits work.
## Junior notes
- Never do disk/network work on the UI thread: use `async`/`runOffUiThread`; never touch Views off the UI thread: use `runOrPostOnUiThread` or `asyncThen`.
- `asyncThen`'s completion runs on the UI thread but the error callback of `async(task, onError)` does not; check which thread your callback assumes before touching UI.
