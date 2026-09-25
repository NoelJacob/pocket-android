# Pocket/src/main/res/layout/fragment_original_web_bottom_sheet.xml

## What this is

This layout is the bottom-sheet menu over the original-web view with page actions such as open-in-browser and share.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `OriginalWebBottomSheetFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragmentOriginalWebBottomSheetBinding` class wires views to code).

ViewModels `OriginalWebBottomSheetViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

## Key pieces

- `viewModel` (com.pocket.app.reader.internal.originalweb.overlay.bottomsheet.OriginalWebBottomSheetViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/drawerIndicator` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/content` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/mainActionButton` (`com.pocket.ui.view.themed.ThemedLinearLayout`): structural container for positioning children
- `@id/saveIcon` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/saveLabel` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/listenButton` (`com.pocket.ui.view.themed.ThemedLinearLayout`): structural container for positioning children
- `@id/shareButton` (`com.pocket.ui.view.themed.ThemedLinearLayout`): structural container for positioning children
- `@id/savedItemActions` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/divider1` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/switchToArticleButton` (`com.pocket.ui.view.themed.ThemedLinearLayout`): structural container for positioning children
- ...plus 4 more ids (dividers, spacers, constraints).

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.

- Bottom sheet: hosted in a `BottomSheetDialogFragment`, slides up from the bottom and dismisses on swipe-down; test half-expanded and full-expanded states.
