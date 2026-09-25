# Pocket/src/main/res/layout/fragment_reader_initial.xml

## What this is

This layout is the brief loading screen shown when a reader page first opens, before the article content is ready.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `InitialFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragmentReaderInitialBinding` class wires views to code).

ViewModels `InitialViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

Included or previewed by: `reader_graph.xml`.

## Key pieces

- `viewModel` (com.pocket.app.reader.internal.initial.InitialViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.
