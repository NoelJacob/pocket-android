# Pocket/src/main/java/com/pocket/app/settings/rotation/AndroidOSRotationLock.java
## What this is
This watches the system auto-rotate setting (the OS-level orientation lock) and yields Pocket's in-app rotation lock whenever the OS lock turns on. It reads Settings.System.ACCELEROMETER_ROTATION at startup and subscribes to changes so a mid-session OS toggle is honored immediately. It is purely an observer — the actual lock state lives in the RotationLock it is given.
## How it fits
Created alongside Pocket's rotation-lock machinery for an activity; startObserving() registers a ContentObserver on the accelerometer-rotation URI, stopObserving() unregisters it (pair with the activity lifecycle to avoid leaking the observer). When the observer sees the OS lock engage while Pocket's lock is held, it releases Pocket's lock so the two never fight.
## Key pieces
- `checkForOSRotationLock()`: ACCELEROMETER_ROTATION == 0 means the OS locked orientation — WHY read at construction: the observer only fires on change, so the initial state must be polled.
- `startObserving()` / `stopObserving()`: ContentObserver registration on the settings URI; onChange re-reads and unlocks Pocket's RotationLock when both locks would otherwise be on.
- `isLocked()`: reports the OS state for callers deciding which lock indicator to show.
## Junior notes
- ContentObserver callbacks arrive on the Handler's thread (here the registering thread) — keep onChange light; it already is just one settings read plus a conditional unlock.
- Forgetting stopObserving leaks the observer past the activity — always pair start/stop with onStart/onStop or onResume/onPause of the owning component.
