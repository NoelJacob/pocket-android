# Pocket/src/main/res/layout/view_loading_dialog.xml

## What this is

This layout is the generic modal loading spinner dialog.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `FetchingDialog` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewLoadingDialogBinding` class wires views to code).

## Key pieces

- `@id/splash` (`LinearLayout`): structural container for positioning children
- `@id/progress_loading` (`com.pocket.ui.view.progress.RainbowProgressCircleView`): structural container for positioning children
- `@id/message_loading` (`TextView`): content region updated by the host

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
