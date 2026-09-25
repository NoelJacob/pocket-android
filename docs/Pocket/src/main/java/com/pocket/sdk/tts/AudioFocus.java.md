# Pocket/src/main/java/com/pocket/sdk/tts/AudioFocus.java
## What this is
This manages Android audio focus for Listen: the OS-level permission that decides which app may make noise right now. When a phone call, navigation prompt, or another music app wants the speaker, this class pauses or ducks Listen; when focus returns, it resumes. It wraps both the old `requestAudioFocus` API and the Android 8.0+ (`API 26`) `AudioFocusRequest` API behind one class.
## How it fits
`ListenMediaSession` creates one `AudioFocus` via the `compat()` factory and passes a `Control` (play / pause / pause-transiently callbacks wired to `Listen`). `Listen` calls `request()` before starting sound and `abandon()` when pausing or stopping. Focus-loss callbacks arrive from the OS on `onAudioFocusChange()`, which translates them into those control calls so playback policy lives in one place.
## Key pieces
- `compat(context, audioAttributes, control)`: factory that returns the modern `AudioFocus26` subclass on API 26+ and the legacy version below. WHY: the two OS APIs are incompatible, and callers shouldn't branch on version.
- `request()`: asks the OS for focus and interprets the three outcomes: granted (play now), failed (stay paused), delayed (wait for a later grant callback). The delayed case exists so playback can auto-start once a transient interruption ends.
- `onAudioFocusChange(focusChange)`: maps OS events to behavior — gain resumes (if we were waiting or ducked), permanent loss pauses for good, transient loss/can-duck pauses but remembers to resume. The `synchronized (lock)` blocks guard the resume/delayed flags because callbacks arrive on different threads.
- `Control` interface: the tiny play/pause/pause-transiently contract the focus handler needs. WHY an interface: `AudioFocus` stays decoupled from the full `Listen` class.
- `AudioFocus26`: builds an `AudioFocusRequest` with speech-tuned audio attributes, delayed-focus acceptance, and pause-when-ducked. WHY a subclass: only the request/abandon mechanics differ by API level.
## Junior notes
- Audio focus on Android is cooperative, not enforced: if you forget to request it, two apps just talk over each other. Always pair `request()` before noise with `abandon()` after.
- `AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK` versus `LOSS_TRANSIENT`: both pause here (rather than lowering volume) because ducking spoken articles is unintelligible. Don't "optimize" this to ducking without product input.
- Callbacks can arrive on any thread; the `lock` object protects `resumeOnFocusGain` / `isPlaybackDelayed`. Read those flags only inside the lock.
