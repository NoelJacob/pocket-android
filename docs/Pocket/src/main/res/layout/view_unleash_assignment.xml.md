# Pocket/src/main/res/layout/view_unleash_assignment.xml

## What this is

This layout is one feature-flag assignment row on the internal Unleash debug screen.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `UnleashDebug` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewUnleashAssignmentBinding` class wires views to code).

Included or previewed by: `activity_unleash.xml`.

## Key pieces

- `@id/name` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/variant` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/payload` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/bottomMargin` (`Space`): structural container for positioning children

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
