# Pocket/src/main/res/layout/view_listen_speed_controls.xml

## What this is

This layout is the listen speed picker popup (0.5x-3x style rate choices).

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `ListenSpeedControlsPopup` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewListenSpeedControlsBinding` class wires views to code).

## Key pieces

- `@id/listen_speed_inc` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/listen_speed_dec` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/listen_speed` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
