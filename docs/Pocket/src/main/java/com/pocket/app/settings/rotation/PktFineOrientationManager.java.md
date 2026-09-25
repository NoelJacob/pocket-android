# Pocket/src/main/java/com/pocket/app/settings/rotation/PktFineOrientationManager.java
## What this is
This detects physical device rotations while Pocket's in-app rotation lock is engaged. Normally a locked activity gets no configuration-change events, so this OrientationEventListener (an Android sensor listener reporting 0–359 degree angles) stays active only while locked and fires OnNewRotationListener whenever the device moves decisively into a new quadrant — which is the signal to re-show the "tap to rotate" unlock widget.
## How it fits
Implements the FineOrientationManager interface used by Pocket's RotationLock flow: the owning component calls markCurrentOrientation() when the unlock widget is shown, setOnNewRotationListener() for the callback, and setEnabled(true/false) with the lock state so the sensor only runs while locked (saving battery).
## Key pieces
- `FINE_ORIENTATION_THRESHOLD (10 degrees)` + quadrant switch in onFineOrientationChange: WHY the threshold — sensor angles jitter, so a new rotation only counts when the angle leaves a ±10° band around the last-shown orientation.
- `ORIENTATION_UNKNOWN` early return: flat-on-table readings carry no direction and must not trigger the widget.
- `getSurfaceRegion(rotation)`: maps a fine angle back to a Surface rotation bucket (ROTATION_0/90/180/270) stored in lastShownForOrientation for the next comparison.
- `markCurrentOrientation()`: seeds lastShownForOrientation from the live display rotation so the first comparison has a baseline.
## Junior notes
- lastShownForOrientation is static (shared across instances) — deliberate, since only one lock widget shows at a time, but be aware a second manager would share the baseline.
- setEnabled(false) when unlocked is load-bearing for battery: OrientationEventListener holds the sensor; leaving it enabled burns power for no benefit.
