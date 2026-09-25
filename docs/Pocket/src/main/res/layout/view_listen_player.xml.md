# Pocket/src/main/res/layout/view_listen_player.xml

## What this is

This layout is the listen player bar: play/pause, scrub position, and speed entry point.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `ListenPlayerView`, `ListenItemAdapter` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewListenPlayerBinding` class wires views to code).

## Key pieces

- `@id/handle` (`com.pocket.ui.view.bottom.BottomSheetDragHandle`): structural container for positioning children
- `@id/listen_playing_from` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/listen_settings` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/coverflow` (`com.pocket.app.listen.CoverflowView`): structural container for positioning children
- `@id/listen_headline` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/listen_subhead` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/listen_current_time` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/listen_time_left` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/scrubber` (`com.pocket.ui.view.themed.ThemedSeekBar`): structural container for positioning children
- `@id/listen_progress` (`ProgressBar`): structural container for positioning children
- ...plus 4 more ids (dividers, spacers, constraints).

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
