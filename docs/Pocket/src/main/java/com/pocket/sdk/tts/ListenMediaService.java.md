# Pocket/src/main/java/com/pocket/sdk/tts/ListenMediaService.java
## What this is
This is the Android OS bridge for Listen playback: a `MediaBrowserServiceCompat` (the system component that advertises audio to lock screens, Bluetooth, Android Auto, and media buttons) that publishes the current article and playback state and routes incoming transport commands back into Listen. It also owns the `ListenNotification` (the persistent playback notification with play/pause/skip buttons).
## How it fits
`ListenMediaSession.startSession()` connects to this service when Listen turns on; the service then subscribes to `Listen.states()` and republishes every snapshot as OS-level metadata (title, artist, art) and playback state (playing, paused, buffering, stopped). In the other direction, OS callbacks (`onPlay`, `onPause`, `onSkipToNext`, seek, stop) call `Controls` on `Listen` with background analytics context. Fast-forward/rewind are fixed 15-second jumps. It stops publishing when `Listen` goes off.
## Key pieces
- `onCreate()`: wires the media session, its transport callback, the notification, and the `Listen.states()` subscription. The exported-service warning matters: intents here can come from any app, so extras are untrusted.
- `publishState(state)`: translates `ListenState` into `PlaybackStateCompat` plus `MediaMetadataCompat`, toggles session active/inactive, and forwards to the notification. Progress display is gated on the accurate-duration feature so TTS mode (estimated times) doesn't show a lying seek bar.
- Transport callback (`onPlay/onPause/onSkipToNext/onSkipToPrevious/onFastForward/onRewind/onSeekTo/onStop`): the OS-facing remote control. Play is guarded on `session.isActive()` so stale button presses can't resurrect a stopped session.
- `fixAndroidOreo()`: plays a silent sound when the session activates on Android 8.0 because that OS version only routes media buttons to sessions that recently played local audio. WHY the hack: device TTS output alone didn't count.
- `BASE_CAPABILITIES / SEEK_DURATION / ALBUM_ART_SIZE`: the advertised action set, the 15s skip distance, and the 300dp artwork size. Capabilities flow into what buttons the lock screen and Auto show.
## Junior notes
- This is a Service with its own lifecycle (`onCreate/onDestroy`), not a UI controller. The RxJava subscription must be disposed in `onDestroy` or it leaks the service after the OS kills it.
- `session.setActive(true/false)` controls whether the OS routes media buttons here. `Listen` STOPPED must deactivate, or Pocket steals play buttons from other apps.
- Album art loads async via the image pipeline with a placeholder fallback; `publishState` may be called again when art arrives. Never block this path on image IO.
