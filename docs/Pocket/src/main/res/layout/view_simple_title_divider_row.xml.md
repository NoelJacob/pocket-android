# Pocket/src/main/res/layout/view_simple_title_divider_row.xml

## What this is

This layout is a simple titled divider row used between grouped list sections.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `AddNewTagModule`, `ItemsTaggingFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewSimpleTitleDividerRowBinding` class wires views to code).

## Key pieces

- `@id/text` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/divider` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
