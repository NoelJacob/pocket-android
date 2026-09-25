# Pocket/src/main/java/com/pocket/app/reader/internal/article/textsettings/fontSettings/FontSettingsBottomSheetViewModel.kt
## What this is
Builds the font-picker rows and applies the user's choice. Each row carries the font name, whether to show Premium/upgrade badges, whether it's currently selected, and its id; tapping a free font applies it immediately, tapping a premium font without Premium routes to the paywall.
## How it fits
Hilt-provided to `FontSettingsBottomSheetFragment`; `FontSettingsAdapter` renders `fontChoiceUiState` and forwards taps to `onFontSelected`. Reads come from `DisplaySettingsManager.FontOption` (the enum of available fonts), `DisplaySettingsManager.currentFont` (the active one), `PocketCache` (premium status / upgrade availability), and `StringLoader` (localized font names). Writes go to `displaySettingsManager.setFont(fontId)`, which re-renders the article behind the sheet.
## Key pieces
- `fontChoiceUiState: StateFlow<List<FontChoiceUiState>>` — the row list, rebuilt by `refreshList()` from every `FontOption`. `StateFlow` is an observable state stream holding the latest value.
- `onInitialized()` — single call from the fragment that builds the initial list.
- `onFontSelected(fontId)` — looks up the option by id (crashes with `!!` if unknown, which means the enum and UI disagreed — a programming error, not user input); premium-gated fonts emit `GoToPremium` for non-premium users, otherwise the font is applied and the list rebuilt so the checkmark moves.
- `onUpClicked()` — header chevron, emits `ReturnToTextSettings`.
- `FontChoiceUiState` — `fontName`, `premiumIconVisible`, `upgradeVisible`, `isSelected`, `fontId`; badge logic is decided here, the adapter only toggles visibility.
## Junior notes
- Premium state is read from the cached `PocketCache`, not fetched fresh — the sheet reflects login/purchase state as of open; reopening refreshes it.
- `refreshList()` after `setFont` is what moves the selected check: the adapter diffs old vs new list and rebinds the two changed rows.
