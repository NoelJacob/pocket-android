# Pocket/src/main/res/layout/view_listen.xml

## What this is

This layout is the text-to-speech (listen) player UI hosted in the reader.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `ListenView` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewListenBinding` class wires views to code).

## Key pieces

- `@id/scrim` (`View`): structural container for positioning children
- `@id/media_bottom_sheet` (`com.pocket.ui.view.themed.ThemedFrameLayout`): structural container for positioning children
- `@id/listen_playlist` (`com.pocket.ui.view.themed.ThemedRecyclerView`): content region updated by the host
- `@id/listen_sticky_player` (`com.pocket.ui.view.visualmargin.VisualMarginConstraintLayout`): structural container for positioning children
- `@id/listen_sticky_handle` (`com.pocket.ui.view.bottom.BottomSheetDragHandle`): structural container for positioning children
- `@id/listen_sticky_headline` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/listen_sticky_subhead` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/listen_sticky_controls` (`com.pocket.app.listen.ListenControlsView`): structural container for positioning children
- `@id/listen_sticky_divider` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/listen_error` (`com.pocket.ui.view.notification.PktSnackbar`): structural container for positioning children
- ...plus 6 more ids (dividers, spacers, constraints).

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
