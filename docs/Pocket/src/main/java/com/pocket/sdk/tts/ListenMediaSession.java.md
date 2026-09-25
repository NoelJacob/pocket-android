# Pocket/src/main/java/com/pocket/sdk/tts/ListenMediaSession.java
## What this is
This is the container for all the fiddly Android audio plumbing Listen needs beyond actual sound output: audio focus (permission to make noise), the headphone-unplug ("becoming noisy") receiver, the shared speech audio attributes, and the connection to `ListenMediaService` that exposes lock-screen and Bluetooth controls. It exists so `Listen` makes three simple calls — start, request focus, stop — while the OS-version differences hide here.
## How it fits
`Listen` creates one of these at startup and calls `startSession()` when Listen turns on (which connects a `MediaBrowserCompat` to `ListenMediaService` and registers the noisy receiver while playing), `requestAudioFocus()` before every playback start, `abandonFocus()` on pause/stop, and `stopSession()` when Listen turns fully off. The `AudioFocus.Control` callbacks passed in the constructor route focus-loss events back to pausing `Listen`. `AndroidMediaPlayer` reads the shared `AUDIO_ATTRIBUTES` from here so both engines sound like speech to the OS.
## Key pieces
- `startSession() / stopSession()`: connects/disconnects the media-browser link that makes the OS show Pocket's controls. The connection callbacks null out the browser on suspend/fail so a dead link is never reused.
- `requestAudioFocus() / abandonFocus()`: thin delegates to the internal `AudioFocus` object. WHY separate methods: callers shouldn't know focus even exists as a separate class.
- `AUDIO_ATTRIBUTES` (usage media + content speech): tells the OS this is spoken word, which affects volume-stream routing, ducking, and Bluetooth behavior. Both players share this one constant so focus requests and `MediaPlayer` setup agree.
- `BecomingNoisyReceiver`: pauses on `ACTION_AUDIO_BECOMING_NOISY` (headphones yanked, Bluetooth dropped) so the article doesn't blare from the speaker. It registers only while `PlayState.PLAYING` to avoid waking the app pointlessly.
- `setNoisyReceiverActive(active)`: idempotent register/unregister guarded by a boolean flag. WHY the guard: double-registering a receiver throws.
## Junior notes
- Audio attributes are fixed for Listen; the class javadoc warns against reusing this for music without changing them. Speech versus music routing genuinely differs on some devices.
- The noisy receiver must be unregistered with the same Context state it registered with; the active-flag plus `stopSession()` cleanup is what guarantees that across rapid on/off toggles.
- `requestAudioFocus()` returning false means "stay paused". Starting playback anyway talks over phone calls or navigation — fail closed.
