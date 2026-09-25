# Pocket/src/main/res/layout/view_previous_bar_dark.xml

## What this is

This layout is the dark-theme previous-article bottom bar.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `OriginalWebFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewPreviousBarDarkBinding` class wires views to code).

Reuses shared chunks: `view_previous_dark.xml`.

## Key pieces

- Static hierarchy with no databinding variables or ids: purely structural/styling.

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
