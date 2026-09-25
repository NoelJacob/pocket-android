# Pocket/src/main/java/com/pocket/app/listen/ListenControlsView.kt

## What this is
The compact row of Listen playback buttons used in the mini player and sticky player: play/pause, skip ±15s, previous/next track, playback speed, and archive.

## How it fits
Embedded wherever a small player strip appears (`ListenView`'s sticky/mini areas). The host calls `bind(state, controls, shouldShowDegradedPlayer)` on every `ListenState` update; button taps call into `Controls` (playToggle, seekTo, previous/next, setSpeed, remove) so this view never touches the audio engine directly.

## Key pieces
- `bind(...)` — WHY: the single render pass; updates the speed label, archive availability, and swaps icons/visibility per `PlayState` (playing shows skip buttons, paused shows prev/next, buffering shows the spinner ring).
- Speed popup wiring — WHY: tapping the speed button shows a `ListenSpeedControlsPopup` dropdown; plus/minus step the speed by 0.1x within 0.5–4x via `controls.setSpeed`.
- Archive button — WHY: archives the current track through the undoable action (so an Undo bar appears) and removes it from the queue via `controls.remove`.
- `SEEK_DURATION` (15s) — WHY: the fixed skip distance for both skip buttons.
- Degraded-player icons — WHY: when text-to-speech voices are unavailable, skip buttons fall back to plain prev/next glyphs.

## Junior notes
- `controls` and `state` are cached from the last `bind` for click handlers — taps before the first bind would crash, so the host must bind immediately after inflation.
- Speed formatting uses the device locale with up to 1 decimal plus "x" suffix; `initSpeedButtonFormat` sets that up once.
