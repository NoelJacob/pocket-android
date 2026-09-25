# Pocket/src/main/res/layout/view_collection_skeleton_tablet.xml

## What this is

This layout is the grey-box loading placeholder shown while a collection loads (tablet).

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `CollectionSkeletonView` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewCollectionSkeletonTabletBinding` class wires views to code).

Reuses shared chunks: `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_hero_card_skeleton.xml`.

## Key pieces

- `@id/title` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/author` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/intro1` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/intro2` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/intro3` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/heroCard1` (`include`): structural container for positioning children
- `@id/heroCard2` (`include`): structural container for positioning children
- `@id/heroCard3` (`include`): structural container for positioning children
- `@id/heroCard4` (`include`): structural container for positioning children
- `@id/heroCard5` (`include`): structural container for positioning children
- ...plus 1 more ids (dividers, spacers, constraints).

## Junior notes

- Skeleton: grey-box placeholder shown while real data loads; keep its shape parallel to the real card so the swap does not jump.
