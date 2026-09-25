# Pocket/src/test/java/com/pocket/util/java/MutableClock.kt
## What this is
A test `Clock` (time source interface) with a settable `time` field; `now()` returns whatever the test assigned. Lets time-dependent logic (expiry, scheduling) be driven deterministically by advancing a number.
## How it fits
Injected wherever production takes a `Clock`: `SessionShould` advances it past expirations to test session rotation. Exists because the real clock (`Clock.SYSTEM`) cannot be fast-forwarded. Note: `ReviewPromptShould` defines its own file-local `MutableClock` rather than importing this one.
## Key pieces
- `MutableClock(var time)` — public mutable millis field; WHY: tests write time directly.
- `now()` — returns `time`; WHY: satisfies the `Clock` interface with zero logic.
## Junior notes
- Times are raw millis (`long`); use `Milliseconds.HOUR/MINUTE/YEAR` constants for readability instead of magic numbers.
- Mutating `time` backwards is allowed by the type but meaningless — always advance forward to simulate elapsed time.
