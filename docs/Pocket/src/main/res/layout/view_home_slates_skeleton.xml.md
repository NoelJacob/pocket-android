# Pocket/src/main/res/layout/view_home_slates_skeleton.xml

## What this is

This layout is the grey-box loading placeholder for the whole Home slate feed.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `SlatesSkeletonView` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewHomeSlatesSkeletonBinding` class wires views to code).

Reuses shared chunks: `view_home_slate_skeleton.xml`, `view_home_slate_skeleton.xml`, `view_home_slate_skeleton.xml`.

## Key pieces

- `@id/slate1` (`include`): structural container for positioning children
- `@id/slate2` (`include`): structural container for positioning children
- `@id/slate3` (`include`): structural container for positioning children

## Junior notes

- Skeleton: grey-box placeholder shown while real data loads; keep its shape parallel to the real card so the swap does not jump.
