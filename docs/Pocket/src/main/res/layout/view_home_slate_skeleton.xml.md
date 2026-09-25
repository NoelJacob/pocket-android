# Pocket/src/main/res/layout/view_home_slate_skeleton.xml

## What this is

This layout is the grey-box loading placeholder for one Home slate section.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Reuses shared chunks: `view_home_slate_hero_card_skeleton.xml`, `view_home_slate_minor_card_skeleton.xml`, `view_home_slate_minor_card_skeleton.xml`.

Included or previewed by: `view_home_slates_skeleton.xml`.

## Key pieces

- `@id/slateName` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/heroCard` (`include`): structural container for positioning children
- `@id/minorCard1` (`include`): structural container for positioning children

## Junior notes

- Skeleton: grey-box placeholder shown while real data loads; keep its shape parallel to the real card so the swap does not jump.
