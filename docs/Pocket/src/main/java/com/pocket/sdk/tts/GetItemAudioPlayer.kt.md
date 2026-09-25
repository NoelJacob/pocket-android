# Pocket/src/main/java/com/pocket/sdk/tts/GetItemAudioPlayer.kt
## What this is
This is the server-audio implementation of `ListenPlayer`: instead of synthesizing speech on the device, it asks Pocket's server (`v3/getItemAudio`) for a pre-rendered audio file of the article, then plays that file through `AndroidMediaPlayer`. Think of it as "podcast mode" for articles versus `TTSPlayer`'s "robot-voice mode". It prefers the Opus format and falls back to MP3.
## How it fits
`Listen` picks this player when the streaming engine is enabled (see `ListenEngine.Streaming`). The app flow is: user presses play in Listen UI -> `Listen` calls `load(track)` here -> this class syncs a `GetItemAudio` thing over the network (a sync here = a request/response against Pocket's API that brings the audio URLs into the local store) -> hands the best URL to `AndroidMediaPlayer`. `preloadNext(itemId)` warms both the API response cache and the audio buffer for the upcoming article so auto-advance feels instant. Errors surface as `ListenError` values that `Listen` turns into player state.
## Key pieces
- `load(track, loaded)`: entry for "make this article playable". Resets any current stream, then emits a debounced (300ms) load request keyed by item ID. WHY debounce plus `distinctUntilChanged`: rapid skips collapse into one network call for the final article.
- `loadItemAudio(result, loaded)`: picks Opus first, MP3 second, network-error if neither is available. Forgets the cached `GetItemAudio` holder afterward so stale URLs don't linger. WHY Opus first: smaller and better quality at low bitrates.
- `preloadNext(itemId)`: remembers the `GetItemAudio` request in a session holder (so `load()` can reuse it without re-syncing) and pre-buffers the Opus URL in `AndroidMediaPlayer`. Null clears the preloaded slot.
- `STREAMING_VOICE`: a fake single `ListenState.Voice` (type `v3/getItemAudio`) because server audio has no voice choice. Its presence tells the UI to hide the voice picker.
- `toListenError()`: maps low-level `AndroidMediaPlayer.Error` values (timeouts, IO, malformed) onto user-facing `ListenError` (timed-out, network, media-player). WHY: UI only branches on the small `ListenError` set.
- `ListenPlayer by streamingPlayer` delegation (Kotlin class delegation = automatically forwarding interface methods to a helper object): all raw transport calls (`play`, `pause`, `seekTo`, progress streams) forward to `AndroidMediaPlayer`; only loading logic lives here.
## Junior notes
- This player always needs network (`isNetworkConnectionRequired = true`). Offline articles still need this call to succeed; the article text being cached is not enough.
- `GET_ITEM_AUDIO_HOLDER` is a session-scoped cache slot. `pocket.remember` pins the request, `pocket.forget` releases it after use. Forgetting to forget leaks the cached thing for the session.
- `switchMap` on the load-request stream cancels the previous in-flight sync when a new article is requested. That is why fast skipping doesn't pile up network calls.
