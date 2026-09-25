# Pocket/src/main/res/layout/fragment_collection.xml

## What this is

This layout is the collection reader screen: a curated set of story cards with a skeleton loading state.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `CollectionFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragmentCollectionBinding` class wires views to code).

ViewModels `CollectionViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

Included or previewed by: `reader_graph.xml`.

## Key pieces

- `viewModel` (com.pocket.app.reader.internal.collection.CollectionViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/toolbar` (`com.pocket.app.reader.toolbar.ReaderToolbarView`): interactive element the host fragment/adapter wires up
- `@id/scrollView` (`com.pocket.ui.view.themed.ThemedNestedScrollView`): scrollable container
- `@id/pocketMark` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/title` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/author` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/intro` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/storyList` (`com.pocket.ui.view.themed.ThemedRecyclerView`): content region updated by the host
- `@id/errorTitle` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/errorMessage` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.
