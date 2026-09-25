# Pocket/src/main/res/layout/fragment_font_settings_bottom_sheet.xml

## What this is

This layout is the reader font-choice sheet: the list of Premium typefaces plus line/word spacing.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `FontSettingsBottomSheetFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragmentFontSettingsBottomSheetBinding` class wires views to code).

ViewModels `FontSettingsBottomSheetViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

## Key pieces

- `viewModel` (com.pocket.app.reader.internal.article.textsettings.fontSettings.FontSettingsBottomSheetViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/backButton` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/list` (`com.pocket.ui.view.themed.ThemedRecyclerView`): content region updated by the host

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.

- Bottom sheet: hosted in a `BottomSheetDialogFragment`, slides up from the bottom and dismisses on swipe-down; test half-expanded and full-expanded states.
