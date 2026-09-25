# Pocket/src/main/java/com/pocket/app/SaveExtension.kt
## What this is
A one-setting Hilt singleton: whether the quick-save overlay (the Pocket-styled confirmation card with tag shortcut) appears when saving from other apps. It is a thin wrapper around the per-user `add_overlay` boolean pref, defaulting to on, plus a one-time repair for users upgraded from the buggy 7.25.0.0 release that had flipped everyone's setting off.
## How it fits
`AddActivity` reads `isOn` to choose between the overlay experience (custom theme, `AddOverlayView`, Pocket toasts) and the plain share path (stock Android toast, immediate finish). `App` exposes it as `saveExtension()`, and the settings screen binds the toggle to the same pref.
## Key pieces
- `isOn` getter/setter: direct pref delegate; WHY a wrapper class instead of raw pref access is a named home for the repair logic and a greppable seam for the feature.
- Init repair block: when `versioning.isUpgrade && from() == 7.25.0.0`, resets to true; WHY version-pinned is the bug existed in exactly one release, so only those upgraders need the fix and later users keep their explicit choice.
## Junior notes
- `prefs.forUser` means the setting resets on logout by design; do not "fix" that by switching to `forApp`, or accounts would inherit each other's save preference.
- `VersionUtil.toVersionCode` packs semver into a comparable int; compare with `versioning.from()`/`upgraded(...)`, never with string comparison.
