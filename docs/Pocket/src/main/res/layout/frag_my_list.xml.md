# Pocket/src/main/res/layout/frag_my_list.xml

## What this is

This layout is the Saves list screen: the filterable, sortable list of everything saved to Pocket.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `MyListFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragMyListBinding` class wires views to code).

ViewModels `MyListViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

Included or previewed by: `main_graph.xml`.

## Key pieces

- `viewModel` (com.pocket.app.list.MyListViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/my_list_content` (`com.pocket.ui.view.themed.ThemedConstraintLayout`): structural container for positioning children
- `@id/dummyFocus` (`View`): structural container for positioning children
- `@id/addButton` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/appBar` (`androidx.constraintlayout.widget.ConstraintLayout`): structural container for positioning children
- `@id/myListChip` (`com.pocket.ui.view.chip.PocketChip`): structural container for positioning children
- `@id/archiveChip` (`com.pocket.ui.view.chip.PocketChip`): structural container for positioning children
- `@id/carousel` (`HorizontalScrollView`): scrollable container
- `@id/searchChip` (`com.pocket.ui.view.chip.PocketChip`): structural container for positioning children
- `@id/listenChip` (`com.pocket.ui.view.chip.PocketChip`): structural container for positioning children
- `@id/allChip` (`com.pocket.ui.view.chip.PocketChip`): structural container for positioning children
- ...plus 23 more ids (dividers, spacers, constraints).

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.
