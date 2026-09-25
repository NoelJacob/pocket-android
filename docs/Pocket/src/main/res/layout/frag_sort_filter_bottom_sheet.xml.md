# Pocket/src/main/res/layout/frag_sort_filter_bottom_sheet.xml

## What this is

This layout is the sort-and-filter bottom sheet over the Saves list (newest/oldest, filter by type/state).

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `FilterBottomSheetFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragSortFilterBottomSheetBinding` class wires views to code).

ViewModels `FilterBottomSheetViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

## Key pieces

- `viewModel` (com.pocket.app.list.filter.FilterBottomSheetViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/viewed` (`com.pocket.ui.view.checkable.CheckableTextView`): content region updated by the host
- `@id/notViewed` (`com.pocket.ui.view.checkable.CheckableTextView`): content region updated by the host
- `@id/shortReads` (`com.pocket.ui.view.checkable.CheckableTextView`): content region updated by the host
- `@id/longReads` (`com.pocket.ui.view.checkable.CheckableTextView`): content region updated by the host
- `@id/newest` (`com.pocket.ui.view.checkable.CheckableTextView`): content region updated by the host
- `@id/oldest` (`com.pocket.ui.view.checkable.CheckableTextView`): content region updated by the host
- `@id/shortest` (`com.pocket.ui.view.checkable.CheckableTextView`): content region updated by the host
- `@id/longest` (`com.pocket.ui.view.checkable.CheckableTextView`): content region updated by the host

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.

- Bottom sheet: hosted in a `BottomSheetDialogFragment`, slides up from the bottom and dismisses on swipe-down; test half-expanded and full-expanded states.
