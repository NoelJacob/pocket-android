# Pocket/src/main/java/com/pocket/sdk/util/wakelock/WakeLockManager.java
## What this is
The app's single manager for wake locks (Android reservations that keep the CPU on with the screen off). It tracks every holder, creates the real system lock only while the app is in the background, adds a short release buffer against rapid re-acquire, and force-releases plus reports locks held past their safety timeout. All Pocket wake locks must go through it so batteries stay healthy and Play vitals stay clean.
## How it fits
A Hilt singleton (one app-wide instance with dependencies provided automatically) injected into pools (`WakefulTaskPool`), receivers (`WakeLockBroadcastReceiver`), and features; `WakefulAppService.Component` listens via `setListener()` and runs a service while locks are held. `acquire()` / `release()` are the only API; lifecycle callbacks (`onUserPresent` / `onUserGone`) flip every lock between foreground bookkeeping and real background locks.
## Key pieces
- `acquire(holder)` / `release(holder)` — reference a lock by holder name; re-acquiring during the release buffer reactivates instead of stacking. WHY: task pools churn acquire/release rapidly and must not flap the real system lock.
- `LockState` (`FOREGROUND`, `BACKGROUND`, `RELEASE_BUFFER`, `RELEASED`) — foreground tracks intent without touching the power manager; background holds a real `PARTIAL_WAKE_LOCK`; release waits 5 seconds before truly unlocking. WHY: most lock traffic happens while the screen is on, where a real lock is pure overhead.
- `Lock.background(start)` — takes the system lock and arms the safety net: fixed-timeout post, optional warning report, or recurring liveliness check. WHY: every background lock carries its own dead-man's switch.
- `Lock.foreground()` — clears timers and drops the system lock. WHY: no lock is ever physically held while the user is active.
- `timeout()` — quietly releases an over-held lock and removes it (error reporting currently gated off). WHY: a leaked lock costs battery; self-healing beats waiting for user complaints.
- `foregroundHolder` ("app") — a standing lock acquired on present, released on gone, giving async `onPause` work a few seconds to claim its own lock. WHY: callbacks firing just after backgrounding would otherwise miss their window.
- `WakeLockException` — debug report with holder name, seconds since creation and since backgrounding, plus the holder's timeout snapshot. WHY: on-call sees what stuck and for how long without reproducing.
## Junior notes
- All public methods are synchronized and timer callbacks re-enter on the main thread (via a main-looper `Handler`); keep `OnTimeout.onTimedOut()` and `LivelinessCheck.keepAlive()` fast and non-blocking or they stall every lock operation.
- Only `PARTIAL_WAKE_LOCK` is used (CPU on, screen off); this manager cannot keep the screen on, so screen-pinning needs a different API.
