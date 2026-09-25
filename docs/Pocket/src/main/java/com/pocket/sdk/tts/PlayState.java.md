# Pocket/src/main/java/com/pocket/sdk/tts/PlayState.java
## What this is
This is the small state machine for Listen playback: seven named values covering off, starting, playing, paused (three flavors), buffering, and error. Every piece of Listen UI — player sheet, notification, media session, lock screen — renders from this single enum carried inside `ListenState`.
## How it fits
`Listen` sets the playstate as it moves through startup (`STOPPED` -> `STARTING` -> `BUFFERING`/`PLAYING`), user pauses, transient audio-focus ducking, and failures, publishing each step on `states()`. `ListenMediaService` maps these values onto OS playback states (which buttons appear on the lock screen), and `Controls.play()` behaves differently from `STOPPED` (build the queue) versus `PAUSED` (just resume).
## Key pieces
- `STOPPED`: fully off, no player or playlist held. The only state where `play()` means "start from the top of the list".
- `STARTING`: transitional while the player and playlist initialize. UI should show a spinner, not a play button; errors here mean setup failed, not playback failed.
- `PLAYING / PAUSED`: the steady states. Paused keeps the queue and position so resume is instant; stopped does not.
- `PAUSED_TRANSIENTLY`: paused because another app briefly took audio focus (a call, a voice prompt). Differs from `PAUSED` because focus-gain auto-resumes from here but never from a user pause.
- `BUFFERING`: player is ready but the current article's audio/data is still loading. Streaming shows this with progress; TTS rarely lingers here.
- `ERROR`: stuck until resolved; `ListenState.error` holds the specific `ListenError`. Clearing the error must also leave this state or UI stays on the error screen.
## Junior notes
- Check for `PLAYING` with `==`, but check "is actively making noise" as playing-or-buffering where the OS is concerned (`ListenNotification` treats both as playing for the pause affordance).
- `PAUSED_TRANSIENTLY` must never be written to prefs as the user's intent. If the app restarts from this state, restore as `PAUSED`.
- New states are expensive: every `switch` in UI, service, and notification must handle them. Prefer reusing these seven over adding an eighth.
