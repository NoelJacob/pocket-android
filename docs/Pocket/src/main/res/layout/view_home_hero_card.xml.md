# Pocket/src/main/res/layout/view_home_hero_card.xml

## What this is

This layout is the large hero story card at the top of a Home slate.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `CollectionStoryAdapter`, `DetailsAdapter` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewHomeHeroCardBinding` class wires views to code).

Included or previewed by: `fragment_collection.xml`, `fragment_home_details.xml`.

## Key pieces

- `@id/heroCard` (`com.pocket.app.home.views.HeroCardView`): structural container for positioning children

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
