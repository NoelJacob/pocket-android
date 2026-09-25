# Pocket/src/main/res/layout/frag_report_item_bottom_sheet.xml

## What this is

This layout is the report-a-story bottom sheet with report reason options.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `ReportItemBottomSheetFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragReportItemBottomSheetBinding` class wires views to code).

ViewModels `ReportItemBottomSheetViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

## Key pieces

- `viewModel` (com.pocket.app.home.slates.overflow.report.ReportItemBottomSheetViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/rootLayout` (`com.pocket.ui.view.themed.ThemedLinearLayout`): structural container for positioning children
- `@id/brokenRadioButton` (`com.pocket.ui.view.themed.ThemedRadioButton`): interactive element the host fragment/adapter wires up
- `@id/wrongCategoryRadioButton` (`com.pocket.ui.view.themed.ThemedRadioButton`): interactive element the host fragment/adapter wires up
- `@id/sexuallyExplicitRadioButton` (`com.pocket.ui.view.themed.ThemedRadioButton`): interactive element the host fragment/adapter wires up
- `@id/offensiveRadioButton` (`com.pocket.ui.view.themed.ThemedRadioButton`): interactive element the host fragment/adapter wires up
- `@id/misinformationRadioButton` (`com.pocket.ui.view.themed.ThemedRadioButton`): interactive element the host fragment/adapter wires up
- `@id/otherRadioButton` (`com.pocket.ui.view.themed.ThemedRadioButton`): interactive element the host fragment/adapter wires up
- `@id/otherEditText` (`com.pocket.ui.view.edittext.PktEditText`): interactive element the host fragment/adapter wires up
- `@id/submitButton` (`com.pocket.ui.view.button.SubmitButton`): interactive element the host fragment/adapter wires up

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.

- Bottom sheet: hosted in a `BottomSheetDialogFragment`, slides up from the bottom and dismisses on swipe-down; test half-expanded and full-expanded states.
