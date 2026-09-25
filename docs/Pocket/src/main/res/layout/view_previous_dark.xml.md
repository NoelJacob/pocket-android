# Pocket/src/main/res/layout/view_previous_dark.xml

## What this is

This layout is the dark-theme previous-article card body.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Included or previewed by: `view_previous_bar_dark.xml`, `view_previous_next_bar_dark.xml`.

## Key pieces

- `@id/previousItem` (`LinearLayout`): structural container for positioning children

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
