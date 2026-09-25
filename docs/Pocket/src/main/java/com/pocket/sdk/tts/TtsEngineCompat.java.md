# Pocket/src/main/java/com/pocket/sdk/tts/TtsEngineCompat.java
## What this is
This lists every text-to-speech engine app installed on the device (Google TTS, Samsung TTS, and friends) on all Android versions, including ones too old for the framework's built-in engine query. It queries the package manager (the OS registry of installed apps) for services that answer the TTS-service intent and wraps each hit in a small descriptor.
## How it fits
`TtsEngines` calls `getEngines()` at construction to learn what voices are available and whether the user's saved preference is still valid. The returned list feeds the engine picker dialog and the default-engine fallback (highest-priority engine when no preference is stored).
## Key pieces
- `getEngines(context)`: package-manager query for `android.intent.action.TTS_SERVICE`, mapped through `getEngineInfo` and sorted. Never returns null (empty list when nothing found or the query itself returns null). WHY the intent action string is hardcoded: it matches `TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE` on versions where that constant doesn't exist.
- `getEngineInfo(resolve, pm)`: builds one descriptor from a query hit — package name, human label (falling back to package name when the label is blank), icon resource, priority. Null service info is skipped rather than crashing on a malformed entry.
- `isSystemEngine(info)`: flags engines shipped in the system image via `FLAG_SYSTEM`. Sorting puts system engines first, then higher priority first, so the default pick is the most trustworthy voice on the device.
- `EngineInfoCompat`: the descriptor itself (name/label/icon/priority/system) with a `compareTo` implementing that system-then-priority order. Private constructor: only this class creates them, from real query results.
## Junior notes
- `MATCH_DEFAULT_ONLY` limits results to engines that present as default TTS providers. Dropping that flag would surface non-user-facing services that can't actually speak.
- Sorting lives in the descriptor's `compareTo`, so `Collections.sort(engines)` is the whole ordering policy. The "first engine is the default" logic in `TtsEngines` depends on it — don't re-sort downstream.
- Package names are the stable identity here, not labels. Labels are localized and can change; prefs store `name`.
