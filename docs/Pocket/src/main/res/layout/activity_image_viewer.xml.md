# Pocket/src/main/res/layout/activity_image_viewer.xml

## What this is

This layout is the full-screen image viewer for opening an article image.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `ImageViewerActivity` (databinding = XML layouts bound to ViewModel fields, so the generated `ActivityImageViewerBinding` class wires views to code).

## Key pieces

- `@id/image_viewer` (`com.pocket.app.reader.internal.article.image.ImageViewer`): interactive element the host fragment/adapter wires up
- `@id/appbar` (`com.pocket.ui.view.AppBar`): structural container for positioning children
- `@id/overlay` (`com.pocket.ui.view.themed.ThemedConstraintLayout`): structural container for positioning children
- `@id/arrow_left` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/arrow_right` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/caption` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
