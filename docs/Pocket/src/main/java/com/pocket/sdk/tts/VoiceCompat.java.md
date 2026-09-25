# Pocket/src/main/java/com/pocket/sdk/tts/VoiceCompat.java
## What this is
This gives the whole app one uniform way to list and set TTS voices across Android versions. Newer Android exposes rich `Voice` objects; older versions only expose locales (language codes like en-US). This class wraps both so callers always deal in `VoiceCompat.Voice` regardless of OS level.
## How it fits
`TTSPlayer` calls `getVoices(tts)` to populate `ListenState.voices` (which feeds the voice picker via `NamedVoice`) and `setVoice(tts, voice)` to apply the user's choice to the active `TextToSpeech` engine (the OS service that synthesizes speech). `ListenState.Voice` is the shared super-interface, so the same voice field can hold either a device voice or the streaming backend's fake voice. Voice-preference restore in `TTSPlayer` maps the saved pref back through this layer at startup.
## Key pieces
- `getVoices(tts)`: returns the available-voice set, currently via the API-21 implementation with automatic fallback to the locale-based one when a device/engine throws (some manufacturer engines crash on `getVoices()`). WHY the try/catch fallback: a throwing engine must degrade to fewer voices, not crash Listen.
- `setVoice(tts, voice)`: dispatches to `tts.setVoice()` for real voice objects or `tts.setLanguage()` for locale-backed ones, returning the standard ERROR/SUCCESS code. Callers check the return rather than assuming the switch worked.
- `Voice` (abstract) + `Gender`: the unified shape — quality, name, features, gender, locale, network requirement — plus a `TYPE` tag of `tts` identifying device voices versus streaming. Gender is guessed from name substrings (female/male/unknown) purely for picker labeling.
- `Api21` (inner): thin wrapper around framework `android.speech.tts.Voice` with equals/hashCode delegated to the wrapped object so set membership and preference matching work.
- `Api1` (inner): synthesizes one voice per supported locale by probing `isLanguageAvailable` across all locales, keeping the best match per language (exact locale beats language-only).
## Junior notes
- `IMPL` is hardcoded to `Api21`; the old-OS path only runs as a fallback, not by version check. Don't add version branches at call sites — the fallback already covers it.
- `getVoices()` can be slow and can throw on bad engines; both are handled inside. Never call framework `tts.getVoices()` directly elsewhere or you lose the safety net.
- Voice equality matters for "is the saved voice still installed" checks. Compare via the wrapper's equals, not by name string, since names can repeat across engines.
