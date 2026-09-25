# Pocket/src/main/res/layout/view_listen_controls.xml

## What this is

This layout is the listen transport controls: skip forward/back and play state.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `ListenControlsView` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewListenControlsBinding` class wires views to code).

## Key pieces

- `@id/listen_controls_space_left` (`Space`): structural container for positioning children
- `@id/listen_speed` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/listen_skip_back` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/listen_prev` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/listen_play_pause` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/listen_skip` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/listen_next` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/listen_archive` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/listen_controls_space_right` (`Space`): structural container for positioning children
- `@id/listen_play_pause_ring` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
