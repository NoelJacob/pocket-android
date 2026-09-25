# Pocket/src/test/java/com/pocket/app/session/ItemSessionsShould.kt
## What this is
Mockito-based tests for `ItemSessions`, which fires reading/listening engagement actions (start, end, pause, continue) per item URL. It proves each segment transition emits the right action, app-background pauses reading, starting a reading session ends listening, and session-id lookup returns null for closed or unknown URLs.
## How it fits
Guards production `com.pocket.app.session.ItemSessions`, built here with `AppMode.PRODUCTION`, a mocked `Analytics` listener, `Clock.SYSTEM`, and real `Prefs(MemoryPrefStore())`. Verifies the action stream that downstream analytics consumes.
## Key pieces
- `analytics` mock plus `itemSessions` subject — WHY: all assertions are `verify(...)` on emitted actions.
- Start/end/pause/continue tests — single- and multi-segment sequences; WHY: pins the segment-to-action mapping (soft close pauses, hard close ends).
- App-background / reading-preempts-listening tests — cross-session rules; WHY: only one active engagement session per modality.
- Session-id tests (active returns id, closed/wrong-URL returns null) — WHY: callers use the id to attribute events, null means no session.
## Junior notes
- Mockito (`mock`/`verify`/`verifyNoMoreInteractions`) is used here instead of MockK; follow the file's existing style when adding cases.
- `ActionContext` (`UI_CONTEXT`) carries the surface that triggered the action; tests reuse one constant context.
