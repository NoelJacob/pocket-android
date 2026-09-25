# Pocket/src/main/java/com/pocket/sdk/tts/TtsEngines.java
## What this is
This remembers which text-to-speech engine the user picked (Google TTS versus Samsung TTS, for example), detects when that choice went stale because engines were installed or removed, and shows the "which voice engine?" picker dialog. It is the preference layer over the raw engine list from `TtsEngineCompat`.
## How it fits
`TTSPlayer` (via `Listen`) consults `getPreferredTtsEngine()` when initializing device speech so it speaks with the right engine; `Listen` checks `isPreferenceExpired()` at startup to decide whether to re-prompt. The last-known engine set and the preferred package name persist in app prefs (`TTS_ENGINE`, `TTS_ENGINES_LAST_KNOWN` as a JSON array), so the staleness check survives restarts.
## Key pieces
- Constructor: loads the live engine list, compares it against the last-known set (size plus membership), resolves the saved preference against what's still installed, and falls back to the highest-priority engine when the favorite is gone. All three outcomes (available list, preferred engine, expired flag) are computed once up front so callers get consistent answers.
- `isPreferenceExpired()`: true when the installed set changed AND more than one engine exists. WHY the count guard: with a single engine there is nothing to choose, so re-prompting would just nag.
- `getPreferredTtsEngine() / getEngines()`: the resolved default (possibly null when nothing is installed) and the full sorted list for the picker.
- `showPicker(context, listener)`: builds an alert dialog of engine labels; on tap it persists the choice plus the current set snapshot and fires `onSelected`, on dismiss `onCanceled`. With zero engines it routes to the install-required dialog instead of showing an empty picker.
- `setPreferred() / saveLastKnownEngines() / getLastKnownEngines()`: pref read/write helpers. The snapshot is saved at selection time, which is what makes the next staleness comparison meaningful.
## Junior notes
- The expiry check is membership-based, not order-based: reordering installs doesn't expire the preference, adding or removing one does. Don't "simplify" it to a size check alone.
- `getPreferredTtsEngine()` can return null (no engines). Callers must handle null by showing the install flow, not by dereferencing.
- `OnTtsEngineSelectedListener` has both selected and canceled paths. Treat cancel as "keep the old preference", not as consent for the fallback engine.
