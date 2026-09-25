# Pocket/src/main/java/com/pocket/app/session/AppSession.java
## What this is
This is the app-wide "is the user using Pocket right now" tracker. It wraps a Session state machine with a 20-minute expiration: starting any user-facing Activity (or TTS playback) opens a segment, and the session id (sid — a server-facing timestamp id) stays stable until all segments close plus 20 idle minutes pass. It also cleans up per-session offline cache files when a new session begins.
## How it fits
A Hilt singleton (one shared instance app-wide, constructor params provided automatically) injected wherever a sid is needed for analytics or cache tagging. AbsPocketActivity starts/closes segments automatically, so most screens get session tracking free; special components (like audio) call startSegment/closeSegment manually. assetUser() hands the offline cache (Assets) a low-priority tag for the current sid, and the registered cleaner deletes other sids' files at next launch.
## Key pieces
- `SESSION_EXPIRATION (20 min)`: WHY quick app-switches count as one session but coming back later starts a new one.
- `getSid()`: returns the current session id, generating a new timestamp-based one if expired.
- `startSegment(segment)` / `closeSegment(segment)`: mark a user-perceived component active/inactive; thread-safe (synchronized) since activities and playback threads call concurrently.
- `expire()`: test-only forced expiry — caller must kill the process after, since in-memory state is left mid-transition.
- `assetUser()`: builds the cache tag tying downloaded files to this sid so stale-session files can be reaped.
## Junior notes
- Session.Segment is an empty marker interface — any object identity works; AppSession does not care what the component is, only how many are open.
- All methods are synchronized because segments open/close from different threads; do not add blocking work inside them.
