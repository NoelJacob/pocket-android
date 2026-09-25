# Pocket/src/main/java/com/pocket/app/session/ItemSessions.java
## What this is
This is the reading-metrics tracker: it records when a user starts, pauses, continues, and ends reading (or listening to) a specific item, firing matching analytics actions to the server. It keeps one active Session per item URL with a 3-minute expiration, plus READING and LISTENING segments so audio and on-screen reading of the same article share a session id. If the user opens a different article mid-read, the old session ends and a new one starts.
## How it fits
A singleton Feature (a gated app capability) observing app lifecycle; ReaderFragment and the Listen player call startSegment/softCloseSegment/hardCloseSegment with the item URL, item id, trigger event, and UI context. Those calls become item_session_start/pause/continue/end sync actions via the inner Analytics class. onUserGone pauses an active reading session when the app backgrounds.
## Key pieces
- `READING_SEGMENT` / `LISTENING_SEGMENT`: marker objects distinguishing on-screen reading from audio; when Listen advances while a reading session is foreground-active, the Listen session is ignored so Reader takes precedence.
- `startSegment(...)`: creates a fresh Session on URL change (ending the old one), then fires start vs continue based on prior state (INACTIVE/EXPIRED→start, PAUSED→continue, ACTIVE→nothing).
- `softCloseSegment(...)` (pause, resumable) vs `hardCloseSegment(...)` (end, final): soft keeps the sid alive for 3 minutes, hard closes it; both no-op for unknown URLs.
- `getSessionId(url)` / `getSessionId()`: expose the active sid only when the session is ACTIVE and the URL matches — used to tag related events.
- `Analytics` inner class: builds the four item_session_* actions with sid, URL, time_spent, and trigger, queued via pocket.sync for background upload.
## Junior notes
- Soft-close vs hard-close maps to "user might come back" (backgrounded, switched article briefly) vs "definitely done" — pick soft when the sid should survive a quick return.
- Prefs (itsess_url/id/wlse/tp) persist session state across process death; a stale non-zero sid resumes as PAUSED, which is why the constructor path matters.
