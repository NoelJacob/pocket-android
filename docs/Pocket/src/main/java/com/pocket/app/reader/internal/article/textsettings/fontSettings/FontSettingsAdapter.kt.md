# Pocket/src/main/java/com/pocket/app/reader/internal/article/textsettings/fontSettings/FontSettingsAdapter.kt
## What this is
Renders the font-picker list: one row per typeface showing its name set in that typeface, a selected checkmark, and Premium/upgrade badges where applicable. Tapping a row selects the font via the ViewModel.
## How it fits
Created by `FontSettingsBottomSheetFragment` and fed by `FontSettingsBottomSheetViewModel.fontChoiceUiState` (an observable state stream collected with `repeatOnCreated`, which auto-cancels when the view is destroyed). Row taps call back into `viewModel.onFontSelected(state.fontId)`; the DiffUtil callbacks animate list refreshes after a selection changes the checkmark.
## Key pieces
- `FontChoiceViewHolder.bind(state)` — sets the name text, resolves the actual `Typeface` from `FontOption` by id so the row previews the font, toggles `premiumIcon` / `selectedCheck` / `upgrade` visibility, and wires the row click.
- `DIFF_CALLBACK` — rows are the same item when `fontName` matches; contents match only on full equality, so toggling selection rebinds that row's checkmark.
## Junior notes
- Resolving the typeface per-bind via `FontOption.values().find { ... }` is a small linear scan over a handful of fonts — fine here, don't "optimize" it into a cache.
- Like the other settings lists, the adapter observes the ViewModel directly and calls `submitList()` — the fragment never touches list data.
