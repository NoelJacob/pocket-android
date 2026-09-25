# Pocket/src/main/res/layout/frag_recent_saves_overflow_bottom_sheet.xml

## What this is

This layout is the overflow menu for a Recent Saves card on Home.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `RecentSavesOverflowFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragRecentSavesOverflowBottomSheetBinding` class wires views to code).

ViewModels `RecentSaveOverflowViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

## Key pieces

- `viewModel` (com.pocket.app.home.saves.overflow.RecentSaveOverflowViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/markAsViewed` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/markAsViewedIcon` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/markAsViewedText` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/shareItem` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/shareIcon` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/archiveItem` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/archiveIcon` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/deleteItem` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/deleteIcon` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.

- Bottom sheet: hosted in a `BottomSheetDialogFragment`, slides up from the bottom and dismisses on swipe-down; test half-expanded and full-expanded states.
