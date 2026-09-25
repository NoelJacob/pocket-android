# Pocket/src/main/java/com/pocket/sdk/util/wakelock/WakeLockHolder.java
## What this is
The identity and safety policy for one user's wake lock (an Android reservation that keeps the CPU on with the screen off). It names the lock holder and declares how long the lock may be held before `WakeLockManager` warns or force-releases it. Every wake lock in the app goes through one of these; raw power-manager locks are never held directly.
## How it fits
Created via `withTimeout()` (most work) or `withLivelinessCheck()` (long/indefinite work like audio) and passed to `WakeLockManager.acquire()` / `release()`. Holders appear in `WakefulTaskPool`, `WakeLockBroadcastReceiver`, and feature code; their names surface in device battery settings and in timeout reports.
## Key pieces
- `withTimeout(name, warnTimeout, stopTimeout, onTimeout)` — fixed-budget lock; warn logs, stop force-releases after that many background minutes. WHY: the common case is bounded work that must never leak past its budget (Google Play flags hour-long locks).
- `withLivelinessCheck(name, checkInterval, check, onTimeout)` — lock kept alive by periodic `keepAlive()` votes instead of a fixed deadline. WHY: playback-style work is legitimately long but must still prove it is making progress.
- `name` — unique app-wide identity and equality key; user-visible in settings. WHY: same-name acquire/release pairs match regardless of object instance.
- `OnTimeout.onTimedOut()` — extra debug string attached to timeout reports (no personal data). WHY: tells on-call which queue or file was stuck, e.g. `WakefulTaskPool`'s queue snapshot.
- `LivelinessCheck.keepAlive()` — true keeps the lock, false releases it. WHY: playback that stopped changing state releases instead of draining the battery.
## Junior notes
- Timeouts only count background time and restart each time the user leaves the app; a lock held across many short foreground sessions can live longer than its minutes suggest.
- `stopTimeout` must be over 0 and ideally under 60; needing longer means `withLivelinessCheck` is the right API, not a bigger number.
