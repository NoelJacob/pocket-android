# Pocket/src/main/res/layout/view_add_overlay.xml

## What this is

This layout is the save-to-Pocket confirmation overlay with its stroke highlight.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `AddOverlayView` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewAddOverlayBinding` class wires views to code).

## Key pieces

- `@id/overlay_root` (`com.pocket.ui.view.themed.ThemedLinearLayout`): structural container for positioning children
- `@id/saved` (`com.pocket.ui.view.themed.ThemedLinearLayout`): structural container for positioning children
- `@id/save_icon` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/save_label` (`com.pocket.ui.view.checkable.CheckableTextView`): content region updated by the host
- `@id/divider` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/tag` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/loading` (`com.pocket.ui.view.progress.FullscreenProgressView`): structural container for positioning children

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
