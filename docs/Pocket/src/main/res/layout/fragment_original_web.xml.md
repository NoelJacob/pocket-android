# Pocket/src/main/res/layout/fragment_original_web.xml

## What this is

This layout is the original-web fallback screen: the publisher's live web page in a WebView for saves that have no parsed article view.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `OriginalWebFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragmentOriginalWebBinding` class wires views to code).

ViewModels `OriginalWebViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

Included or previewed by: `reader_graph.xml`.

## Key pieces

- `viewModel` (com.pocket.app.reader.internal.originalweb.OriginalWebViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.
