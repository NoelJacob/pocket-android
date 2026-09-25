# Pocket/src/main/res/layout/view_home_error_snack_bar.xml

## What this is

This layout is the Home error snackbar with retry action.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `HomeErrorSnackBar` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewHomeErrorSnackBarBinding` class wires views to code).

## Key pieces

- `@id/title` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/message` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/retryButton` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/retryText` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/progressBar` (`ProgressBar`): structural container for positioning children

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
