# Pocket/src/main/java/com/pocket/app/reader/internal/article/textsettings/TextSettingsBottomSheetViewModel.kt
## What this is
The brains behind the reader's "Aa" display-settings sheet. It owns the stepper enabled-states, the Premium upsell visibility, and the current-font label, and applies every adjustment to `DisplaySettingsManager` (the persisted store that re-renders the article) the moment the user taps.
## How it fits
Hilt-provided to `TextSettingsBottomSheetFragment`, which binds its layout to `uiState` and forwards taps to the `Interactions` methods. Reads come from `DisplaySettingsManager` (current font, min/max limits) and `UserRepository` flows (whether the user has premium display settings, whether an upgrade is available). Writes go straight to `DisplaySettingsManager`; navigation requests (`ShowPremiumScreen`, `ShowFontChangeBottomSheet`) go out as one-shot `events` the fragment handles.
## Key pieces
- `uiState: StateFlow<UiState>` — stepper enabled flags, `premiumSettingsVisible`, `premiumUpsellVisible`, and `fontChangeText` (string-res id of the current font). `StateFlow` is an observable state stream that always holds the latest value.
- `onInitialized()` — seeds the font label, subscribes to the two premium flows with `update`, and calls `recheckLimits()` for the initial stepper states.
- Six stepper methods (`onFontSizeUpClicked` etc.) — each mutates the setting then calls `recheckLimits()` so the +/- buttons grey out exactly at min/max.
- `onLightThemeClicked` / `onDarkThemeClicked` / `onSystemThemeClicked` — delegate to `displaySettingsManager.setTheme` / `setSystemDarkThemeOn` with a null item (app-wide, not per-article).
- `onBrightnessChanged(value)` — converts the 0–100 slider int to a 0–1 float for `setBrightness`.
## Junior notes
- `_uiState.edit { copy(...) }` vs `update { ... }` are equivalent read-modify-write helpers here — don't read ordering significance into the mix.
- Theme/brightness changes apply live to the article behind the sheet because `DisplaySettingsManager` is the single source of truth the article WebView already observes.
