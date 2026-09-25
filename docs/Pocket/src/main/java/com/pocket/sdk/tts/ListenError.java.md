# Pocket/src/main/java/com/pocket/sdk/tts/ListenError.java
## What this is
This is the complete list of things that can go wrong in Listen, as one enum (a fixed set of named values). It covers both backends: on-device TTS failures (nothing installed, init failed, no voices, speech error) and server-audio failures (network, server, timed out, media player), plus playlist-level problems (empty list, article not downloaded, parsing failed, logged out).
## How it fits
Both players (`TTSPlayer`, `GetItemAudioPlayer`/`AndroidMediaPlayer`) emit these values on their error streams; `Listen` stores the latest one in `ListenState.error` and moves to `PlayState.ERROR`. UI screens switch on this enum to decide what message and fix-it button to show (open TTS settings, go online, pick another article), using the javadoc hints on each value.
## Key pieces
- `NO_TTS_INSTALLED / INIT_FAILED / NO_VOICES`: device TTS is missing, broken, or voiceless. Fix path is `TTSUtils` dialogs that route to system settings or the TTS data installer.
- `ARTICLE_NOT_DOWNLOADED / ARTICLE_PARSING_FAILED`: the article text itself couldn't be prepared (offline download missing versus downloaded-but-unparseable). Only the first is retryable by going online.
- `NETWORK_ERROR / SERVER_ERROR / TIMED_OUT / MEDIA_PLAYER / SPEECH_ERROR`: the network/audio ladder. Network means the call failed, server means it succeeded but the backend errored, timed out means buffering stalled mid-stream, media-player means `MediaPlayer` failed opaquely, speech error means an on-device utterance failed to render.
- `EMPTY_LIST / LOGGED_OUT`: nothing to play (playlist came back with zero items) versus not allowed to play (logged-out users can't use Listen).
## Junior notes
- Each value implies a different UX: retry, settings redirect, or pick-another-article. Never collapse them into a generic "playback failed" without checking with design; the enum granularity is deliberate.
- `PlayState.ERROR` always pairs with a non-null `ListenError` in `ListenState`. If you set one, set the other, and clear both together on recovery.
- New failure modes go here, not in ad-hoc booleans. Adding a value forces every `switch` on it to consider the new case at compile time.
