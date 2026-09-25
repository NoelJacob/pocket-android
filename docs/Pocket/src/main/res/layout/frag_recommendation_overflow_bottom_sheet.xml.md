# Pocket/src/main/res/layout/frag_recommendation_overflow_bottom_sheet.xml

## What this is

This layout is the overflow menu for a recommended story (save, share, report, not-interested).

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `RecommendationOverflowBottomSheetFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragRecommendationOverflowBottomSheetBinding` class wires views to code).

ViewModels `RecommendationOverflowBottomSheetViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

## Key pieces

- `viewModel` (com.pocket.app.home.slates.overflow.RecommendationOverflowBottomSheetViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/shareItem` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/shareIcon` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/reportItem` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/reportIcon` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.

- Bottom sheet: hosted in a `BottomSheetDialogFragment`, slides up from the bottom and dismisses on swipe-down; test half-expanded and full-expanded states.
