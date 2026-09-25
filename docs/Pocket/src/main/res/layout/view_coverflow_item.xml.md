# Pocket/src/main/res/layout/view_coverflow_item.xml

## What this is

This layout is one cover-flow carousel item (used by legacy onboarding/showcase art).

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `CoverflowItemView` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewCoverflowItemBinding` class wires views to code).

## Key pieces

- `@id/coverflow_item` (`com.pocket.ui.view.item.ItemThumbnailView`): structural container for positioning children

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
