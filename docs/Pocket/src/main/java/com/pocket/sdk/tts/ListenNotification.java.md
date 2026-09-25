# Pocket/src/main/java/com/pocket/sdk/tts/ListenNotification.java
## What this is
This builds and manages the persistent Listen notification: the play/pause/previous/next controls users see in the shade and on the lock screen while an article plays. While audio is playing it runs as a foreground-service notification (a special OS status that keeps the playback process alive); while paused it drops to a regular notification.
## How it fits
`ListenMediaService` creates this object with its media-session token and calls `update(playState, metadata)` on every `ListenState` change. This class reads the current `Track` from `Listen.state()`, builds a `MediaStyle` notification (the OS template that wires hardware/media-button actions to the session) with intents that deep-link back into the article, and either starts foreground or posts normally. When playback stops or there is no current track, it removes the notification.
## Key pieces
- `update(playState, metadata)`: the single entry point, always marshalled onto the UI thread. Branches on playing (start foreground) versus paused (notify + stop foreground) versus stopped/no-track (remove). The `try/catch` around posting exists because some devices throw inside framework notification code; failing gracefully beats crashing playback.
- `newNotification(playState, metadata)`: assembles title (article), artist/album line (author/site), large icon (article art), and the three compact actions (previous, play/pause, next) plus a cancel/close button. Icons and labels swap based on whether the state counts as playing (playing includes buffering so the pause affordance shows during stalls).
- `newMediaButtonIntent(action)`: wraps each control as a media-button pending intent routed through the session, so Bluetooth and lock-screen presses behave identically to taps.
- `pendingIntent / startTime`: the content tap intent (deep link to the current article) and the `setWhen` timestamp. `pendingIntent` is created once per session and `startTime` pins the elapsed-time display; both reset when the notification is removed.
- `unregister()`: cancels the notification and drops foreground status. Called on stop and on posting failures.
## Junior notes
- Foreground status is load-bearing: removing it while playing lets the OS kill audio in the background. Only `stopForeground(false)` (keep the notification visible) on pause, never a full cancel.
- All work posts to the UI thread via `AppThreads`. `Listen.state()` reads must happen there or the track can race with a skip.
- `NOTIFICATION_ID = 424242` is fixed so each update replaces the last instead of stacking. Don't make it dynamic.
