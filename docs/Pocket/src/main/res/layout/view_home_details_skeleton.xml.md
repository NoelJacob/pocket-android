# Pocket/src/main/res/layout/view_home_details_skeleton.xml

## What this is

This layout is the grey-box loading placeholder for the Home details screen.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `DetailsSkeletonView` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewHomeDetailsSkeletonBinding` class wires views to code).

Reuses shared chunks: `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_hero_card_skeleton.xml`.

## Key pieces

- Static hierarchy with no databinding variables or ids: purely structural/styling.

## Junior notes

- Skeleton: grey-box placeholder shown while real data loads; keep its shape parallel to the real card so the swap does not jump.
