# Pocket/src/main/res/layout/view_not_tagged_item.xml

## What this is

This layout is one row in the untagged-items filter list.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `TagsAdapter` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewNotTaggedItemBinding` class wires views to code).

## Key pieces

- `@id/tagText` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/divider` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/clickableView` (`View`): structural container for positioning children

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
