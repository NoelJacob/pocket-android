# Pocket/src/main/res/layout/fragment_highlights_bottom_sheet.xml

## What this is

This layout is the highlights sheet listing everything highlighted in the current article.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `HighlightsBottomSheetFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragmentHighlightsBottomSheetBinding` class wires views to code).

ViewModels `HighlightsBottomSheetViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

## Key pieces

- `viewModel` (com.pocket.app.reader.internal.article.highlights.HighlightsBottomSheetViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/highlightIcon` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/title` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/divider` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/highlightList` (`com.pocket.ui.view.themed.ThemedRecyclerView`): content region updated by the host

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.

- Bottom sheet: hosted in a `BottomSheetDialogFragment`, slides up from the bottom and dismisses on swipe-down; test half-expanded and full-expanded states.
