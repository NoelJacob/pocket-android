# Pocket/src/main/res/layout/view_home_tablet_details_skeleton.xml

## What this is

This layout is the grey-box placeholder for Home details on tablets.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `DetailsSkeletonView` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewHomeTabletDetailsSkeletonBinding` class wires views to code).

Reuses shared chunks: `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_hero_card_skeleton.xml`.

## Key pieces

- `@id/article1` (`include`): structural container for positioning children
- `@id/article2` (`include`): structural container for positioning children
- `@id/article3` (`include`): structural container for positioning children
- `@id/article4` (`include`): structural container for positioning children
- `@id/article5` (`include`): structural container for positioning children
- `@id/article6` (`include`): structural container for positioning children
- `@id/article7` (`include`): structural container for positioning children
- `@id/article8` (`include`): structural container for positioning children

## Junior notes

- Skeleton: grey-box placeholder shown while real data loads; keep its shape parallel to the real card so the swap does not jump.
