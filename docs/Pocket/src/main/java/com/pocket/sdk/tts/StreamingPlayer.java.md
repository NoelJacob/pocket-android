# Pocket/src/main/java/com/pocket/sdk/tts/StreamingPlayer.java
## What this is
This is the slimmed-down playback contract for server-audio only: play, pause, seek, speed, and progress/buffering/completion streams, with no TTS concepts (no voices, no utterances, no article parsing). It is what `AndroidMediaPlayer` implements and `GetItemAudioPlayer` re-exposes.
## How it fits
`GetItemAudioPlayer` implements both this and the full `ListenPlayer`: raw transport and timing flow straight through to its internal `AndroidMediaPlayer` via Kotlin delegation, while article-to-URL resolution stays in `GetItemAudioPlayer` itself. `Listen` talks to the combined object through `ListenPlayer`; code that only needs transport (progress UI, seek bar) can depend on this smaller type.
## Key pieces
- `play() / pause() / seekTo(position) / isPlaying() / isLoaded()`: the transport core. `isLoaded` means "audio prepared and ready", distinct from "article chosen".
- `getDuration() / getElapsed()`: exact file timings from `MediaPlayer` (ThreeTen `Duration` values, a date/time library for Android). WHY exact matters: streaming is the only mode with a trustworthy seek bar, gated on the accurate-duration feature flag.
- `setSpeed() / setPitch()`: rate controls. Speed is honored; pitch is accepted by the interface but unsupported for fixed recordings (`AndroidMediaPlayer` throws on pitch).
- `getProgressUpdates() / getBufferingUpdates() / getCompletions()`: the three streams — once-per-second ticks while playing, buffering percents, and end-of-file. Completions are what `Listen` uses to auto-advance the queue.
## Junior notes
- Interface segregation in action (small focused interfaces over one fat one): new transport-only features go here; anything TTS-flavored (voices, utterances) belongs on `ListenPlayer`.
- `seekTo` takes an absolute position in the file, not a delta. The 15-second skip buttons compute `elapsed +/- 15s` at the call site (`ListenMediaService`).
- Progress ticks only while playing. Don't use this stream as a heartbeat for paused-state UI.
