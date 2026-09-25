# Pocket/src/main/res/layout/view_reader_toolbar.xml

## What this is

This layout is the reader top toolbar: back, listen, bookmark, share, and overflow actions.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `ReaderToolbarView` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewReaderToolbarBinding` class wires views to code).

ViewModels `ToolbarUiStateHolder` (`toolbarUiStateHolder`), `ToolbarInteractions` (`toolbarInteractions`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

## Key pieces

- `toolbarUiStateHolder` (com.pocket.app.reader.toolbar.ReaderToolbar.ToolbarUiStateHolder): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `toolbarInteractions` (com.pocket.app.reader.toolbar.ReaderToolbar.ToolbarInteractions): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/top_toolbar_container` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/upButton` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/archiveButton` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/ReAddButton` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/saveButton` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/listenButton` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/shareButton` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/overflowButton` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.
