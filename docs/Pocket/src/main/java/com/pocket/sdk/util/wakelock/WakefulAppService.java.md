# Pocket/src/main/java/com/pocket/sdk/util/wakelock/WakefulAppService.java
## What this is
A mostly-empty Android `Service` (a background component whose existence tells the system the app is doing meaningful work) that runs exactly while any wake lock is held. Its presence makes the system less likely to kill Pocket when activities are closed but background work continues. All logic lives in the nested `Component`; the service itself only logs start and destroy.
## How it fits
`Component` is a Hilt singleton observing app lifecycle and `WakeLockManager.setListener()`; when lock state or user presence changes it starts or stops this service. `WakefulTaskPool` and receiver-driven work are the producers whose locks keep it alive. Long term the plan (per the TODO) is for each feature to manage its own wakefulness and delete this central service.
## Key pieces
- `Component.setWakeLocked(value, context)` / `onUserPresent()` / `onUserGone(context)` — track lock state and whether the user is around, re-evaluating on every change. WHY: service lifetime is a pure function of "work exists" and "user present".
- `invalidateService(context)` — starts the service when locks are held (already-running stays while locked; fresh start requires user present to satisfy Android Oreo background-start limits), else stops it, preferring a visible activity context for the start call. WHY: background `startService` throws on modern Android, so the start path needs a foreground context when possible.
- Foreground-tolerant start — swallows the Android P bug where a resumed app still throws "not allowed to start service". WHY: crashing the foreground app to protect background work would be backwards; the next state change retries.
- `onStartCommand()` returns `START_STICKY` — asks the system to recreate the service if killed. WHY: the work it protects (held wake locks) is still outstanding.
## Junior notes
- The swallowed start exception is intentional and logged nowhere; if the service mysteriously is not running while locked, reproduce on Android P+ foreground transitions before assuming the listener broke.
- `onBind()` always returns null (started service, never bound); do not try to bind to it from activities.
