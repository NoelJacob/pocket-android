# Pocket/src/main/res/layout/fragment_text_settings_bottom_sheet.xml

## What this is

This layout is the reader text-settings sheet: font size, line height, and text alignment controls.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `TextSettingsBottomSheetFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragmentTextSettingsBottomSheetBinding` class wires views to code).

ViewModels `TextSettingsBottomSheetViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

## Key pieces

- `viewModel` (com.pocket.app.reader.internal.article.textsettings.TextSettingsBottomSheetViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/settingsView` (`com.pocket.ui.view.menu.DisplaySettingsView`): structural container for positioning children

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.

- Bottom sheet: hosted in a `BottomSheetDialogFragment`, slides up from the bottom and dismisses on swipe-down; test half-expanded and full-expanded states.
