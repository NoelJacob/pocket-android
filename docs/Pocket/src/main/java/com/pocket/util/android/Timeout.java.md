# Pocket/src/main/java/com/pocket/util/android/Timeout.java

## What this is
A restartable one-shot timer built on an Android `Handler`: post a callback after a delay, with cancel and restart. It solves the "show X only if Y is still pending" pattern, like a loading indicator that should appear only when an operation is slow. For example, `PocketUrlHandlerActivity` starts a `Timeout` while resolving a shared URL and shows the "Loading..." toast only if resolution is still running when it fires; finishing early cancels it.

## How it fits
It is instantiated wherever a delayed fallback is needed; the known caller is `PocketUrlHandlerActivity` (loading-toast delay, cancelled in `hideLoadingToast()`). It posts to the main looper's `Handler` by default (or an injected one), invoking the `TimeoutListener` when the delay elapses. Downstream is just the listener callback receiving the `Timeout` itself.

## Key pieces
- `Timeout(runOnTimeout)` / `Timeout(runOnTimeout, timeoutMs)` / `Timeout(runOnTimeout, timeoutMs, handler)`: constructors defaulting to no delay and the main-thread handler. WHY three: tests inject a fake handler while app code uses the defaults.
- `setDelay(delay)`: changes the delay future `start()` calls use. WHY separate: build the timer once, tune the delay later.
- `start(delay)` / `start()`: cancel any pending firing, then post anew. WHY cancel-first: restarting the timer is the normal use (e.g. every keystroke pushes the deadline out).
- `cancel()`: removes the pending callback; safe to call when idle. WHY it exists: the "operation finished in time, stand down" path.
- `TimeoutListener.onTimeout(timeout)`: the single callback. WHY it receives the timer: the listener can restart or inspect it.

## Junior notes
- A delay of 0 behaves like a plain `Handler.post` (run ASAP on the handler thread) — useful for "defer one loop turn" without special-casing.
- The default handler is the main looper, so the callback runs on the UI thread: safe for toasts and views, but never block in it.
- Always pair `start()` with `cancel()` on the matching lifecycle event (activity finish, operation complete) or the callback will fire after its screen is gone.
