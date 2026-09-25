# Pocket/src/main/res/layout/activity_settings.xml

## What this is

This layout is the Settings activity host: a container that shows the preferences fragment.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `AbsPrefsFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `ActivitySettingsBinding` class wires views to code).

## Key pieces

- `@id/rootView` (`com.pocket.ui.view.themed.ThemedRelativeLayout`): structural container for positioning children
- `@id/appbar` (`com.pocket.ui.view.AppBar`): structural container for positioning children
- `@id/loading` (`com.pocket.ui.view.empty.LoadableLayout`): structural container for positioning children
- `@id/list` (`com.pocket.ui.view.themed.ThemedRecyclerView`): content region updated by the host

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
