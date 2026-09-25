# Pocket/src/main/java/com/pocket/sdk/tts/NamedVoice.java
## What this is
This turns raw TTS voices into human-readable picker labels like "Female 1" or "Male 2". Android reports voices with technical names that vary by engine and device, so this helper groups them by detected gender and numbers each group for display.
## How it fits
The Listen voice-picker UI calls `NamedVoice.name(voices, context)` with the `VoiceCompat.Voice` set from the active `TTSPlayer` and gets back a sorted-by-construction list of labeled rows. Each row pairs the display string (from localized resources) with the underlying voice object that `Controls.setVoice()` needs to actually switch voices.
## Key pieces
- `name(voices, context)`: the only logic. Counts male/female/unknown voices separately and emits labels (`tts_voice_female`, `tts_voice_male`, `tts_voice`) with per-group counters. WHY per-group counters: "Female 1, Female 2" is clearer than global numbering when genders mix.
- `sortOrder`: orders female voices first (1...), then male (1001...), then unknown (2001...). WHY the offsets: a plain sort by this int yields a stable, pleasant picker order without a custom comparator in the UI.
- `voice`: the backing `VoiceCompat.Voice` for the row. The UI displays `name` but hands `voice` back on selection; never persist the display string as the preference.
## Junior notes
- Gender comes from substring matching on the engine's voice name ("female"/"male"), so it is a guess. Unknown is common and fine; don't treat it as an error.
- Labels are localized via `R.string` resources. Adding a new category means adding the string resource, not hardcoding text here.
- Server-audio mode (`GetItemAudioPlayer`) has no voice choice, so this picker is TTS-mode only. Gate it on the `MULTIPLE_VOICES` feature flag.
