# Pocket/src/main/res/layout/view_home_tablet_slate_hero_card_skeleton.xml

## What this is

This layout is the grey-box placeholder for a tablet hero card.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Included or previewed by: `view_home_tablet_slate_skeleton.xml`.

## Key pieces

- `@id/image` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/titleLayout` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/collectionLabel` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/title1` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/title2` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/subtitle1` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/subtitle2` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/subtitle3` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/domain` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/author` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- ...plus 2 more ids (dividers, spacers, constraints).

## Junior notes

- Skeleton: grey-box placeholder shown while real data loads; keep its shape parallel to the real card so the swap does not jump.
