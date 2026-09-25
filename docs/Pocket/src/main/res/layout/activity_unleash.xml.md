# Pocket/src/main/res/layout/activity_unleash.xml

## What this is

This layout is the internal feature-flag (Unleash) debug screen listing flag assignments.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `UnleashDebug` (databinding = XML layouts bound to ViewModel fields, so the generated `ActivityUnleashBinding` class wires views to code).

## Key pieces

- `@id/compose` (`androidx.compose.ui.platform.ComposeView`): structural container for positioning children
- `@id/refresh` (`androidx.swiperefreshlayout.widget.SwipeRefreshLayout`): structural container for positioning children
- `@id/assignments` (`com.pocket.ui.view.themed.ThemedRecyclerView`): content region updated by the host

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
