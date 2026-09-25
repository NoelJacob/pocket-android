# Pocket/src/main/res/layout/view_home_slate_minor_card_skeleton.xml

## What this is

This layout is the grey-box placeholder for a minor card while Home content loads.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Included or previewed by: `view_home_recent_saves_skeleton.xml`, `view_home_slate_skeleton.xml`, `view_home_tablet_slate_skeleton.xml`.

## Key pieces

- `@id/titleAndImageLayout` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/image` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/title1` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/title2` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/title3` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/domain` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/timeToRead` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/action1` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/action2` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children

## Junior notes

- Skeleton: grey-box placeholder shown while real data loads; keep its shape parallel to the real card so the swap does not jump.
