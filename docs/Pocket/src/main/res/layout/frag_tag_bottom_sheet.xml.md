# Pocket/src/main/res/layout/frag_tag_bottom_sheet.xml

## What this is

This layout is the tag bottom sheet: recent, suggested, and free-text tag entry for one or more saves.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `TagBottomSheetFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragTagBottomSheetBinding` class wires views to code).

ViewModels `TagBottomSheetViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

## Key pieces

- `viewModel` (com.pocket.app.list.tags.TagBottomSheetViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/tagIcon` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/editTagText` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/overflowButton` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/cancelButton` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/saveButton` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/divider` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/recyclerView` (`androidx.recyclerview.widget.RecyclerView`): content region updated by the host

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.

- Bottom sheet: hosted in a `BottomSheetDialogFragment`, slides up from the bottom and dismisses on swipe-down; test half-expanded and full-expanded states.
