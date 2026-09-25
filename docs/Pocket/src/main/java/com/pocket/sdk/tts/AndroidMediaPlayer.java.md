# Pocket/src/main/java/com/pocket/sdk/tts/AndroidMediaPlayer.java
## What this is
This is the streaming-audio workhorse for the Listen feature. Instead of speaking text on the device (like `TTSPlayer` does), it plays back pre-rendered audio files downloaded from Pocket's server using Android's built-in `MediaPlayer` class (the OS component that plays audio/video from a URL or file). It keeps two internal `Player` slots, called `current` and `next`, so the next article's audio can be preloaded while the current one is still playing.
## How it fits
`GetItemAudioPlayer` owns an `AndroidMediaPlayer` and delegates all raw playback to it: `GetItemAudioPlayer.load()` resolves an article to an audio URL, then calls `load()` here; `Listen` (the central Listen coordinator) drives `play()`, `pause()`, and `seekTo()` through that same chain. Playback progress, buffering percent, completion, and error events flow back out as RxJava observables (RxJava = a library for event streams you subscribe to), which `Listen` subscribes to in order to update `ListenState` and the notification. The audio attributes (usage = media, content = speech) come from `ListenMediaSession`.
## Key pieces
- `load(url, loaded)`: plays the double-buffer trick. If the URL is already in `current` it is a no-op; if it is in `next`, the two slots swap so playback can start instantly; otherwise it loads into `current`. This WHY is gapless-feeling article transitions.
- `preloadNext(url)`: quietly loads the upcoming article's URL into the `next` slot (or resets it when null), so skipping forward feels instant.
- `Player` (private inner class): wraps one `MediaPlayer` plus a `silence` MediaPlayer used as a start-up trick. Handles async `prepareAsync`, buffering callbacks, error mapping, and per-speed retry logic. It exists so the outer class never touches `MediaPlayer` threading directly.
- `Error` enum: translates raw `MediaPlayer` what/extra codes (IO, malformed, unsupported, timed out, server died) into a small set the rest of Listen understands. WHY: raw codes are device-specific and unreadable.
- `getProgressUpdates() / getBufferingUpdates() / getCompletions() / getErrors()`: the four RxJava streams `Listen` observes. Progress only ticks once per second while actually playing, which saves battery versus polling constantly.
- `setSpeed()`: applies the rate to both slots at once, so a speed change survives the swap to the next article.
## Junior notes
- `MediaPlayer` is stateful and callback-driven: you must `reset()` before reusing it and wait for the prepared callback before starting. This class hides that dance; never call the inner `Player` directly.
- `setPitch()` intentionally throws `UnsupportedOperationException`. Server audio is a fixed recording, so pitch-shifting is not supported the way on-device TTS pitch is.
- The `lowestReportedFailingSpeed` preference records speeds that broke playback on a device, so the player can avoid retrying a known-bad rate. If you touch speed logic, keep that guard.
