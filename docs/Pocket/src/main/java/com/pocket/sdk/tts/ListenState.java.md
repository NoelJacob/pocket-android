# Pocket/src/main/java/com/pocket/sdk/tts/ListenState.java
## What this is
This is an immutable snapshot of everything Listen UI needs to render: play state, current article, playlist position, voice, speed, pitch, current spoken chunk, elapsed and total time, buffering, and any error. Immutable means once built it never changes; every update is a brand-new object published to observers.
## How it fits
`Listen` builds these via the `Builder` (usually from `rebuild()` on the previous state plus a few changed fields) and emits them on `states()`, the RxJava observable stream that the player UI, `ListenMediaService`, and `ListenNotification` all subscribe to. Screens read the current value with `Listen.state()` for initial layout and re-render on each emission. `TrackedControls` also reads state to attach item IDs and progress percents to analytics events.
## Key pieces
- State fields (`playstate/current/index/list/utterance/duration/elapsed/bufferingProgress/voice/voices/speed/pitch/autoArchive/autoPlay/error/supportedFeatures`): the full render model. `current` + `index` + `list` locate playback in the queue; `utterance` is the sentence being spoken now (TTS mode); `duration/elapsed` are exact for streaming, estimated for TTS.
- `Voice` interface + `Type`: the smallest thing that identifies a voice choice across backends (type tag like `tts` or `v3/getItemAudio`, name, locale, network requirement). Server audio uses a single fake voice; on-device TTS uses `VoiceCompat.Voice` instances.
- `Feature` enum (`MULTIPLE_VOICES`, `ACCURATE_DURATION_AND_ELAPSED`, `PRELOADING`): capability flags set by the active `ListenEngine` so UI shows only what the backend can do (voice picker, true seek bar, preload spinner).
- `Builder`: copy-and-modify construction (`rebuild().playstate(...).build()`). Requires non-null playstate; `list()` copies defensively so later playlist mutations can't retroactively change a published snapshot.
- `getProgressPercent()`: elapsed-over-duration as 0-100, zero-safe. Used by analytics and progress UI; returns 0 when duration is unknown rather than dividing by zero.
- `equals/hashCode`: full field comparison so `Listen` can suppress duplicate emissions and avoid re-rendering identical states.
## Junior notes
- Never mutate a published state; always `rebuild()`. Observers may hold old snapshots for diffing, and mutation would corrupt that.
- `utterance` is null in streaming mode (there are no per-sentence chunks, just one audio file). Code that highlights text must null-check instead of assuming TTS.
- `bufferingProgress` of -1 means "unknown", not 0%. Treat negative as indeterminate in progress UI.
