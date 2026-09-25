# Pocket/src/main/java/com/pocket/app/session/Session.java
## What this is
This is the small state machine underneath both AppSession and ItemSessions. It tracks a set of open segments and moves between INACTIVE (never started), ACTIVE (at least one segment open), PAUSED (all closed but resumable), and EXPIRED (paused longer than the expiration). The session id is just the start timestamp, persisted in preferences so it survives process death, and getTimeSpent reports active time excluding pauses.
## How it fits
Never used directly by screens — AppSession creates one with a 20-minute expiration for whole-app use, ItemSessions creates one per article URL with a 3-minute expiration. Callers open/close Segments; the owners translate state transitions into analytics (start/continue on open, pause/end on close).
## Key pieces
- `getSid()`: WHY ids are timestamps — generates clock.now() when INACTIVE/EXPIRED (returned as seconds for the server), reuses the stored id otherwise.
- `startSegment(segment)`: adds to activeSegments, flips to ACTIVE; on a fresh open it also accumulates the gap since the last pause into pauseDuration so time-spent math stays honest.
- `softCloseSegment` (pausable, records whenLastSegmentEnded) vs `hardCloseSegment` (final): soft leaves the door open for continue, hard ends the session outright.
- `hasExpired()` / `getState()`: expiry is lazily evaluated — only PAUSED sessions with a last-pause older than the expiration flip to EXPIRED, and getState applies that check on every read.
- `State` enum and `Segment` marker interface: the only types callers need; segments are compared by identity in a HashSet.
## Junior notes
- Expiry is not on a timer — nothing fires at 20 minutes; the next getState/startSegment call after the deadline observes EXPIRED. Do not expect a callback.
- Clock is injectable (not always the wall clock) so tests can simulate time passing; production passes Clock.SYSTEM.
