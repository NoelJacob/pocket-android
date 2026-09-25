# Pocket/src/main/java/com/pocket/sdk/tts/TrackedControls.java
## What this is
This is a decorator (a wrapper that adds behavior to every method of an object without changing its interface) around `Controls` that fires analytics events and item-session segments for every playback action. The wrapped controls do the real work; this class just reports what happened, with which article and how far along, to Pocket's event pipeline.
## How it fits
`Listen.trackedControls(...)` wraps its internal `ListenControls` in one of these before handing it to UI screens, `ListenMediaService` (background/media-button context), or deep links. Every call first delegates to the real controls, then syncs the matching action (`listen_opened`, `start/resume/pause_listen`, skips, seeks, voice/speed changes) with an `ActionContext` carrying item ID, session ID, queue index, and progress. Item sessions (timed engagement segments like the listening segment) open, segment, and close here too.
## Key pieces
- `trackOn() / trackOff()`: emits `listen_opened` / `listen_closed`. `play(track, nodeIndex)` also emits opened when starting from `STOPPED`, since that path implicitly powers on.
- `trackPlay(current) / trackPlay()`: emits `start_listen` vs `resume_listen` based on whether `elapsed` is zero, plus `markAsViewed`, and opens the listening session segment with the right trigger event. The no-arg overload waits for the first non-null current track from the state stream so background play still attributes correctly.
- `trackPause() / trackSkipSession() / trackSeek() / trackVoiceChange() / trackSpeed()`: pause (with soft session close), skip-previous/next (closing the old segment and opening the new one), seek with from/to positions, and voice/speed change events. Skips share one helper because next/previous/moveTo attribute identically apart from the trigger.
- `interaction(current) / buildListenContext(current)`: builds the analytics context — queue index (1-based), item/session IDs — merged with the caller's view or UI-source context. WHY centralized: every event must carry the same identity fields or funnels break.
- `foreground()`: emits an opened-listen segment when the UI comes forward mid-playback. Guarded to playing-with-current-track only so backgroundnoise never fabricates engagement.
## Junior notes
- Delegation runs before tracking (act, then report). If the underlying call throws, no event fires — that ordering is deliberate, don't "fix" it by tracking first.
- `state.get()` is read after the action for event fields like elapsed. Reading it before would attribute the pre-action position to a post-action event.
- The `StateChecker` interface (get + changes) is how this class observes state without depending on all of `Listen`. Tests can stub it instead of booting the singleton.
