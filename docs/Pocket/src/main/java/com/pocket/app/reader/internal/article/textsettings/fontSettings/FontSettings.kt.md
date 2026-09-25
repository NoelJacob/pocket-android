# Pocket/src/main/java/com/pocket/app/reader/internal/article/textsettings/fontSettings/FontSettings.kt
## What this is
The contract for the font-picker bottom sheet (the second-level sheet reached from the "Aa" display settings). Like its parent contract, it only declares roles: how the sheet is initialized, how font rows report taps, how the back chevron works, and which navigation events can result.
## How it fits
`FontSettingsBottomSheetFragment` implements the navigation side (collects `Event`s) while `FontSettingsBottomSheetViewModel` implements `Initializer`, `ListInteractions`, and `ClickListener`. `FontSettingsAdapter` calls `onFontSelected` on row tap.
## Key pieces
- `Initializer.onInitialized()` — loads the font list once when the sheet opens.
- `ListInteractions.onFontSelected(fontId)` — row tap with the chosen font's id; the ViewModel decides between applying it or detouring to Premium.
- `ClickListener.onUpClicked()` — the top chevron that returns to the parent text-settings sheet.
- `Event` — `ReturnToTextSettings` (close picker, reopen parent sheet) or `GoToPremium` (open the paywall for a premium font).
## Junior notes
- The three tiny interfaces separate three callers (fragment lifecycle, list rows, header button) so the ViewModel's public surface documents who calls what.
- `sealed class Event` keeps navigation exhaustive in the fragment's `when` — adding an outcome forces the fragment to handle it.
