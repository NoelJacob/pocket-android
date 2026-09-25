# Pocket/src/main/java/com/pocket/app/settings/cache/CacheLimitSeekbar.java
## What this is
This is the custom slider for the offline-cache size setting. It maps a 0–1000 progress range through an accelerating curve onto megabyte steps (50 MB increments below 500 MB, 100 MB above), with the far-right zone meaning UNLIMITED. It snaps raw drag positions to clean increments and notifies a listener only when the snapped value actually changes.
## How it fits
Embedded in CacheLimitPreferenceView, which lives inside CacheSettingsFragment's CacheLimitPreference row. The fragment sets the position via setProgressInBytes; user drags flow out through OnIncrementedMbProgressChangedListener into the fragment's temp size pref. It never writes the real cache limit itself — save happens via the fragment's Save button.
## Key pieces
- `convertProgressToSnappedMb(progress)`: the core mapping — accelerating interpolator (0.7 factor) spreads small values for precision; >= SCROLLABLE_AREA (0.95) snaps to UNLIMITED.
- `getProgressInSnappedMb()` / `getProgressInBytes()` / `setProgressInBytes(bytes)`: the fragment-facing API translating between slider units, display MB, and real bytes.
- Seek listener trio (onStart/onProgress/onStopTrackingTouch): tracks drag state (mIsTracking), dedupes via mLastSnappedMb, and on release snaps an almost-UNLIMITED drag fully right so the state is unambiguous.
- `setOnIncrementedMbProgressChangedListener`: resets the dedupe marker and immediately fires once so the label paints correctly on bind.
## Junior notes
- Non-linear sliders need the interpolator in BOTH directions (progress→MB and bytes→progress use inverse math) — changing the curve or increments requires updating both converters or the thumb will jump on rebind.
- mLastSnappedMb dedupe means programmatic setProgress to the same snapped value does not refire — rely on the immediate-fire in setOnIncrementedMbProgressChangedListener for initial paint, not on setting progress.
