# Pocket/src/main/res/layout/fragment_reader.xml

## What this is

This layout is the reader shell screen: a container that swaps between the loading view, the parsed article, the original web page, and collection views depending on what is available.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `ReaderFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragmentReaderBinding` class wires views to code).

ViewModels `ReaderViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

Included or previewed by: `main_graph.xml`.

## Key pieces

- `viewModel` (com.pocket.app.reader.ReaderViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/fragmentContainer` (`androidx.fragment.app.FragmentContainerView`): structural container for positioning children
- `@id/previousNextLayout` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/previousItem` (`com.pocket.ui.view.themed.ThemedLinearLayout`): structural container for positioning children
- `@id/nextItem` (`com.pocket.ui.view.themed.ThemedLinearLayout`): structural container for positioning children
- `@id/topDivider` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.
