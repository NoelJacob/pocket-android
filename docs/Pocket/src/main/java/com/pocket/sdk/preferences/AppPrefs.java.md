# Pocket/src/main/java/com/pocket/sdk/preferences/AppPrefs.java
## What this is
Legacy central registry of app settings: dozens of typed preference fields (booleans, ints, longs, floats, strings over SharedPreferences) covering downloads, reader/TTS/listen options, device state, and dev-only flags. New code should not add fields here.
## How it fits
Hilt singleton injected wherever old code needs settings; each field is created in the constructor via `prefs.forUser(...)` (synced per user) or `prefs.forApp(...)` (per device/install). Readers include download logic (`DOWNLOAD_*`), the article/TTS stack (`ARTICLE_TTS_*`, `TTS_ENGINE`), listen features (`LISTEN_*`), and session/version tracking (`PREVIOUS_APP_VERSION`, `SESSION_ID`). `changes()` exposes an RxJava `Observable` (a stream that emits each time any preference changes) for reactive listeners.
## Key pieces
- User vs app split — `forUser` prefs follow the account (reader, TTS voice, wifi-only downloads); `forApp` prefs stay with the install (orientation, screen widths, session id, cached tokens).
- Representative fields — `DOWNLOAD_ONLY_WIFI`, `ALWAYS_OPEN_ORIGINAL`, `ARTICLE_TTS_SPEED/PITCH/VOICE*`, `LISTEN_USE_STREAMING_VOICE`, `ROTATION_LOCK`, `CONTINUE_READING_*`, plus `DEVCONFIG_*` flags owned by `BetaConfigFragment` and never used in production.
- `deprecateUserBoolean(key, default)` — reads a value then deletes the key, the standard one-shot migration helper for retired settings.
## Junior notes
- Marked `@Deprecated`: for new settings, accept a `Preferences` in the owning component's constructor and expose an API on that component instead of adding a global here; only genuinely global prefs still belong in this file.
- `Preferences` here is the typed wrapper (`BooleanPreference`, `StringPreference`, etc.), not raw SharedPreferences; defaults are supplied at creation (e.g. `downloadText=true`, `autoOnlyWifi=true`), and `prefs.group("gsfevc_")` namespaces event counters.
