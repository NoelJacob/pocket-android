# Pocket/src/main/java/com/pocket/app/reader/internal/article/textsettings/TextSettingsBottomSheet.kt
## What this is
The contract for the reader's "Aa" display-settings bottom sheet (font size, line height, margins, theme, brightness, font picker). It declares the user actions the sheet supports and the one-shot navigation events the sheet can request. It holds no logic — it just keeps fragment and ViewModel in sync.
## How it fits
`TextSettingsBottomSheetFragment` collects `Event`s and forwards taps into `Interactions`; `TextSettingsBottomSheetViewModel` implements both interfaces plus `Initializer`. Databinding in the fragment layout calls the Interactions methods and observes the ViewModel's UiState through `SettingsViewBindings`.
## Key pieces
- `Initializer.onInitialized()` — called once from the fragment's `onViewCreated` to load premium flags and stepper limits.
- `Interactions` — one method per control: font-size/line-height/margin up/down, premium upgrade, font-change row, light/dark/system theme, and the brightness slider value.
- `Event` — two navigation outcomes: `ShowPremiumScreen` (open the paywall) and `ShowFontChangeBottomSheet` (swap this sheet for the font picker).
## Junior notes
- Interface-per-role (`Initializer`, `Interactions`) is the codebase's convention for bottom-sheet contracts — the `object` itself is just a namespace, never instantiated.
- `Event` is a `sealed class` (a closed set of subtypes the `when` in the fragment must handle exhaustively), which is why adding a new event forces a compile error in the fragment until handled.
