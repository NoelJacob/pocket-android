# Pocket/src/main/res/layout/view_previous_next_bar_light.xml

## What this is

This layout is the light-theme combined previous/next-article bottom bar.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `OriginalWebFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewPreviousNextBarLightBinding` class wires views to code).

Reuses shared chunks: `view_previous_light.xml`, `view_next_light.xml`.

## Key pieces

- Static hierarchy with no databinding variables or ids: purely structural/styling.

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
