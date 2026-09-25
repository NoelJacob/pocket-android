# Pocket/src/main/java/com/pocket/sdk/tts/Controls.java
## What this is
This is the public remote control for the Listen (text-to-speech) feature: one small interface with every playback command (on, play, pause, next, previous, seek, voice, speed, auto-archive, auto-play). Screens, notifications, media buttons, and deep links all talk to Listen through this instead of touching player internals.
## How it fits
`Listen` implements this interface in its inner `ListenControls` class and hands out instances via `controls()` (plain) and `trackedControls(...)` (wrapped with analytics). Callers like `ListenMediaService` (lock-screen/media buttons), `ListenNotification` actions, and article screens call these methods; `Listen` translates them into playlist plus `ListenPlayer` operations and publishes a new `ListenState` that UI observes.
## Key pieces
- `on() / off()`: power switch. `on()` initializes the player and loads the playlist (showing Listen UI); `off()` tears everything down and clears the playlist. WHY separate from play/pause: being "open but paused" is a real state with visible UI.
- `play() / play(track) / play(track, nodeIndex)`: resume-or-restart semantics. Bare `play()` resumes from `STOPPED` at the list head or from pause; the overloads build a single-item playlist and optionally jump to a saved reading position (`START_FROM_SAVED_POSITION` = resume where the user left off).
- `playToggle() / pause()`: the two pause paths. Toggle is what play/pause buttons call; `pause()` is a hard pause that never resumes by itself.
- `next() / previous() / moveTo(track) / remove(track)`: playlist navigation that preserves the current play/pause state (skipping while paused stays paused). `previous()` restarts the current article if more than ~5 seconds have played, matching music-player convention.
- `seekTo(position) / setVoice / setSpeed / setPitch`: in-article adjustments forwarded to the active `ListenPlayer` (server-audio player ignores pitch). Voice changes restart the current utterance.
- `setAutoArchive / setAutoPlay / foreground()`: behavior flags (archive finished articles, auto-advance) plus a hint that Listen UI entered the foreground for analytics.
## Junior notes
- This is an interface, not behavior. Real logic lives in `Listen.ListenControls`; the `TrackedControls` decorator only adds analytics events around the same calls.
- `Track` here is a playlist row (an article mapped for playback), not an audio track. `moveTo(int)` takes a playlist index, not a timestamp.
- Methods are fire-and-forget: results arrive later via `Listen.states()`, an observable state stream (a flow of `ListenState` snapshots UI subscribes to). Don't expect return values.
