# Pocket/src/main/java/com/pocket/sdk/tts/ListenEngine.kt
## What this is
This is the factory-and-capability contract that lets `Listen` swap between its two playback backends without branching everywhere: server-rendered streaming audio (`GetItemAudioPlayer`) versus on-device text-to-speech (`TTSPlayer`). Each engine object knows how to build its player, whether an existing player is still the right kind, and which UI features that backend supports.
## How it fits
`Listen.updateEngine()` asks the active `ListenEngine` whether the current `ListenPlayer` is still valid via `isValid()`; if not, it calls `createPlayer(...)` to build the replacement and publishes the new `supportedFeatures()` into `ListenState` so UI (voice picker, progress bar, preload indicators) adapts. The streaming-versus-TTS choice itself comes from user prefs and server feature flags; this file only encapsulates the consequences of that choice.
## Key pieces
- `isValid(player)`: type check (`is GetItemAudioPlayer` vs `is TTSPlayer`). WHY: the cheapest correct way to know whether a settings flip requires tearing down the player.
- `createPlayer(context, pocket, threads, android, initialSpeed, lowestReportedFailingSpeed)`: builds a fully wired player. The streaming branch needs network/session/session-audio plumbing; the TTS branch just needs a Context. WHY the asymmetry: server audio delegates to `AndroidMediaPlayer` internally.
- `supportedFeatures()`: declares what the UI can rely on. Streaming advertises accurate durations and preloading (it plays fixed files); TTS advertises multiple voices (it speaks with any installed voice). `ListenState.Feature` values flow straight from here.
- `Streaming` / `Tts` objects: the only two implementations (Kotlin `object` = singleton). Keeping them here side by side makes the capability matrix visible in one glance.
## Junior notes
- `ListenEngine` is `internal`: app code outside this package should talk to `Listen`, never instantiate engines directly.
- Adding a third engine means adding a third object plus its feature set, and teaching `Listen.updateEngine()` when to pick it. The `isValid` convention keeps that change to one place.
- Feature flags here drive UI visibility (e.g. hide the voice picker for streaming). If a feature misbehaves on one backend, check this mapping before touching player code.
