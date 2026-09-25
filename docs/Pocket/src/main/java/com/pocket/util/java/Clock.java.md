# Pocket/src/main/java/com/pocket/util/java/Clock.java
## What this is
A one-method interface, `now()`, that stands in for "what time is it" so time can be faked in tests. It ships two ready-made instances: `SYSTEM` (wall-clock time) and `ELAPSED_REALTIME` (monotonic time since boot).
For example, session code takes a `Clock` and calls `clock.now()` instead of `System.currentTimeMillis()`, so a test can inject a fake clock.

## How it fits
Used by session-expiry code: `AppSession`, `ItemSessions`, and `Session` accept a `Clock` to compute expiration against stored timestamps. Production passes `Clock.SYSTEM`; tests pass a controllable fake. `ELAPSED_REALTIME` (backed by `SystemClock.elapsedRealtime()`) is available for durations that must survive wall-clock changes.

## Key pieces
- `now()`: returns the current time in millis. WHY it exists: the seam that makes time-dependent logic testable.
- `SYSTEM`: `System::currentTimeMillis`. WHY it exists: the default production clock for wall-clock timestamps.
- `ELAPSED_REALTIME`: `SystemClock::elapsedRealtime`. WHY it exists: a monotonic clock for measuring elapsed time, unaffected by the user changing the device clock.

## Junior notes
- A `SystemClock` here is Android's device-clock helper, not a Java concurrency clock. `currentTimeMillis()` can jump (time zones, NTP); `elapsedRealtime()` only moves forward and resets on reboot.
- Java `::` here is a method reference (shorthand for a tiny anonymous implementation). `System::currentTimeMillis` means "a Clock whose now() calls that method".
