# Pocket/src/main/res/layout/view_home_slate_default.xml

## What this is

This layout is a default Home slate section: header plus its story cards.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `SlatesAdapter` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewHomeSlateDefaultBinding` class wires views to code).

Included or previewed by: `fragment_home.xml`.

## Key pieces

- `@id/title` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/subtitle` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/slateSeeAllLayout` (`com.pocket.ui.view.themed.ThemedLinearLayout`): structural container for positioning children
- `@id/heroCard` (`com.pocket.app.home.views.HeroCardView`): structural container for positioning children
- `@id/minorCardRecyclerView` (`com.pocket.ui.view.themed.ThemedRecyclerView`): content region updated by the host

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
