# Pocket/src/main/java/com/pocket/sdk/tts/ListenPlayer.java
## What this is
This is the abstraction over "something that can read an article aloud", hiding whether the sound comes from on-device text-to-speech (`TTSPlayer`) or a server-rendered audio file (`GetItemAudioPlayer`). `Listen` programs against this interface so switching engines never touches playback orchestration.
## How it fits
`Listen` holds one `ListenPlayer` at a time (built by a `ListenEngine`), calling `load(track)` when the article changes and `play/pause/seekTo/playFromNodeIndex` for transport. The player pushes lifecycle events back as RxJava observables (subscribable event streams): ready, started utterances, progress, buffering, completions, and errors. `Listen` subscribes to those streams to advance `ListenState`, auto-play the next article, and record analytics. The article-to-audio path is therefore UI -> `Listen` -> `ListenPlayer.load()` -> (network sync or HTML parse) -> audio out.
## Key pieces
- `load(track, loaded)`: "prepare this article". For TTS that means parsing HTML into utterances; for streaming it means syncing audio URLs (a sync = fetching data from Pocket's server into the local store). The `OnLoaded` callback fires when preparation finishes so `Listen` can resume or stay paused correctly.
- `preloadNext(itemId)`: opportunistic warm-up of the following article. Some backends no-op this; streaming uses it to prefetch URLs and audio bytes. WHY it exists: article transitions are the most latency-visible moment in Listen.
- Transport (`play/pause/seekTo/playFromNodeIndex/isPlaying/isLoaded/getDuration/getElapsed`): the minimal surface `Listen` needs. `playFromNodeIndex` jumps to a paragraph (used for resume-from-saved-position and tap-to-play-from-here); the TODO notes streaming resume isn't fully unified with TTS yet.
- Voice/speed/pitch (`getVoice/setVoice/getVoices/setSpeed/setPitch`): per-engine settings. Streaming exposes one fake voice and ignores pitch (fixed recording); TTS exposes every installed voice. UI branches on `supportedFeatures`, not on the player type.
- Event streams (`getReadies/getStartedUtterances/getProgressUpdates/getBufferingUpdates/getCompletions/getErrors`): the player's outgoing signals. Completions drive auto-advance; started-utterances drive highlighting and progress for TTS; errors carry `ListenError` values UI can act on.
## Junior notes
- `OnLoaded` is a one-method callback, not a future: store the desired end state (resume vs pause) in the callback object because `load()` returns before audio is ready.
- `release()` must be called when `Listen` swaps or shuts down engines; players hold `TextToSpeech` instances, `MediaPlayer`s, and wake locks that leak otherwise.
- Don't add backend-specific methods here. The `ListenState.Feature` mechanism is how backends advertise extra abilities without breaking the other implementation.
