# Pocket/src/test/java/com/pocket/app/session/SessionShould.kt
## What this is
Tests for `Session`, the low-level timed-session tracker (id plus active time). It proves the id is stable while a segment is open, rotates after expiration, survives overlapping segments, expires immediately on hard close, and accumulates active time excluding pauses.
## How it fits
Guards production `com.pocket.app.session.Session`, the primitive under `ItemSessions`. Built with `InMemoryLongPreference` doubles (in-memory long storage) and a `MutableClock` (manually-advanced clock) so expiration and time-spent are deterministic.
## Key pieces
- `setUp()` — `MutableClock` starting at one year of millis; WHY: large base time avoids zero-edge cases.
- Id tests (stable-while-open, rotate-after-expiry, stable-within-expiry, overlapping segments, hard-close expires) — start/soft-close/hard-close sequences with clock advances; WHY: pins id lifecycle rules.
- Time-spent tests (remember after close/pause, active-segments-only) — advance the clock across pauses; WHY: paused time must not inflate engagement.
## Junior notes
- Soft close is pausable (may resume within expiry); hard close is terminal. Mixing them up is the classic failure here.
- `Milliseconds.HOUR/MINUTE/YEAR` are duration constants; expiry comparisons use the injected clock, never wall time.
