# Pocket/src/main/java/com/pocket/sdk/util/PocketActivityRootView.java
## What this is
The root layout of every AbsPocketActivity screen. It inflates the shared ril_root layout holding the PocketActivityContentView plus always-available accessory views: the Listen (text-to-speech) mini-player and rotation-lock controls.
## How it fits
Inflated by AbsPocketActivity, then attach(activity) wires it to that screen. It hosts ListenComponents (observing Listen/ListenState via RxJava streams) and RotationLockComponents, and routes back presses and listen-bar insets on the activity's behalf.
## Key pieces
- `attach(activity)`: connects the activity, finds the content view stub, and spins up listen/rotation components.
- `ListenComponents`: inner lifecycle listener managing the media-controls view stub — shows/hides and expands the Listen bar as playback state changes.
- `onBackPressed`: lets accessory views (e.g. expanded player) consume back first before the activity finishes.
- `getListenViewStates` / `expandListen` / `setListenInsets`: plumbing so screens observe or resize around the audio bar.
## Junior notes
- ViewStub = lazily-inflated placeholder; the Listen bar layout only inflates when audio actually plays. Observables here are RxJava streams (events over time) — dispose them with the activity lifecycle.
