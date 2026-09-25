# Pocket/src/main/res/layout/view_home_slate_wide.xml

## What this is

This layout is a wide-format Home slate section used for full-bleed story rows.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `SlatesAdapter` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewHomeSlateWideBinding` class wires views to code).

## Key pieces

- `@id/title` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/subtitle` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/seeAllLayout` (`com.pocket.ui.view.themed.ThemedLinearLayout`): structural container for positioning children
- `@id/heroCard` (`com.pocket.app.home.views.WideHeroCardView`): structural container for positioning children
- `@id/minorCardRecyclerView` (`com.pocket.ui.view.themed.ThemedRecyclerView`): content region updated by the host

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
