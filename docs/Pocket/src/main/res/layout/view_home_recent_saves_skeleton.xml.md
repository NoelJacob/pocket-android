# Pocket/src/main/res/layout/view_home_recent_saves_skeleton.xml

## What this is

This layout is the grey-box placeholder for the Recent Saves strip while Home content loads.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `RecentSavesSkeletonView` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewHomeRecentSavesSkeletonBinding` class wires views to code).

Reuses shared chunks: `view_home_slate_minor_card_skeleton.xml`, `view_home_slate_minor_card_skeleton.xml`, `view_home_slate_minor_card_skeleton.xml`, `view_home_slate_minor_card_skeleton.xml`.

## Key pieces

- `@id/minorCard1` (`include`): structural container for positioning children

## Junior notes

- Skeleton: grey-box placeholder shown while real data loads; keep its shape parallel to the real card so the swap does not jump.
