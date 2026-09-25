# Pocket/src/main/res/layout/view_add_tags_empty.xml

## What this is

This layout is the empty state of the tag editor when a save has no tags yet.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `EmptyTagListModule` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewAddTagsEmptyBinding` class wires views to code).

## Key pieces

- Static hierarchy with no databinding variables or ids: purely structural/styling.

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
