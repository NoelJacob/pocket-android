# Pocket/src/main/res/layout/frag_item_overflow_bottom_sheet.xml

## What this is

This layout is the per-item overflow menu (archive, favorite, tag, share, delete) for a single save.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `ItemOverflowBottomSheetFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragItemOverflowBottomSheetBinding` class wires views to code).

ViewModels `ItemOverflowBottomSheetViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

## Key pieces

- `viewModel` (com.pocket.app.list.list.overflow.ItemOverflowBottomSheetViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/title` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/image` (`com.pocket.ui.view.item.ItemThumbnailView`): structural container for positioning children
- `@id/addTags` (`com.pocket.ui.view.themed.ThemedConstraintLayout`): structural container for positioning children
- `@id/tagIcon` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/markAsViewed` (`com.pocket.ui.view.themed.ThemedConstraintLayout`): structural container for positioning children
- `@id/viewedIcon` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/archive` (`com.pocket.ui.view.themed.ThemedConstraintLayout`): structural container for positioning children
- `@id/archiveIcon` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/delete` (`com.pocket.ui.view.themed.ThemedConstraintLayout`): structural container for positioning children
- `@id/deleteIcon` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.

- Bottom sheet: hosted in a `BottomSheetDialogFragment`, slides up from the bottom and dismisses on swipe-down; test half-expanded and full-expanded states.
