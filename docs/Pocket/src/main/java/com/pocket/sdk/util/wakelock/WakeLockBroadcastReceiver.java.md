# Pocket/src/main/java/com/pocket/sdk/util/wakelock/WakeLockBroadcastReceiver.java
## What this is
A base class for broadcast receivers (Android components that wake up on system events) that keeps the CPU awake for the whole `doOnReceive()` callback. Subclasses implement only `doOnReceive()`; this class wraps it with a wake-lock acquire and release through `WakeLockManager`. Work done here never races the device falling back asleep.
## How it fits
Extended by `BootReceiver` (re-registers background sync on reboot) and any similar receiver; the system calls `onReceive()`, which builds a short-timeout `WakeLockHolder` named after the class, acquires it from `App.from(context).wakelocks()`, runs the subclass logic, and releases. It is the receiver-side counterpart to `WakefulTaskPool` for background work.
## Key pieces
- `onReceive(context, intent)` — acquire, delegate, release. WHY: guarantees the lock covers exactly the callback, even if the subclass throws (release still runs since there is no early return).
- `doOnReceive(context, intent)` — the subclass hook. WHY: subclasses think only about their event, never about power management.
- One-minute-timeout holder per receive — safety net, not scheduling. WHY: a stuck receiver cannot hold the device awake indefinitely.
## Junior notes
- Keep `doOnReceive()` fast (dispatch and return, like `BootReceiver` does); a receiver holding a wake lock for long I/O blocks the whole broadcast queue.
- The holder name is the class simple name and drives equality in `WakeLockManager`; concurrent broadcasts of the same receiver share one lock rather than stacking.
