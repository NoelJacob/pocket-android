# Pocket/src/main/java/com/pocket/app/listen/ListenSettingsFragment.java

## What this is
The Listen settings UI itself: text-to-speech engine picker, voice list (with preview), playback speed, and related toggles, built on the app's generic preferences framework.

## How it fits
Shown inside `ListenSettingsActivity` via `show(...)`, and reachable from the player gear in `ListenPlayerView`. It reads available engines/voices from the `Listen` text-to-speech service and `VoiceCompat`, persists choices to prefs, and previews voices on tap. `AbsPrefsFragment` (a preferences-screen base class) renders the `Preference` rows it declares.

## Key pieces
- `newInstance()` / `show(...)` + `FragmentLaunchMode` — WHY: standard show-or-reuse launcher so the screen never stacks twice.
- Engine preference (`TtsEnginePreference`) — WHY: picks the system TTS engine; changing it rebuilds the voice list since voices are engine-specific.
- Voice rows with preview — WHY: tapping a voice plays a sample through `Controls` so users can audition before committing.
- Speed and toggle preferences — WHY: persist playback defaults consumed by the player on next launch.
- Analytics context (`CxtView`/`CxtSection`/`CxtEvent`) — WHY: attributes settings changes to the Listen settings surface.

## Junior notes
- Voice enumeration is async (RxJava `CompositeDisposable`); disposables must be cleared with the fragment lifecycle or callbacks land on a dead fragment.
- Some devices report duplicate or locale-mismatched voices — the fragment dedupes and filters, so test voice changes on a real device with multiple engines, not just the emulator.
