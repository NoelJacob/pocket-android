# Pocket/src/main/res/layout/view_home_slate_hero_card.xml

## What this is

This layout is the hero card variant used inside slate sections on phones.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `HeroCardView` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewHomeSlateHeroCardBinding` class wires views to code).

## Key pieces

- `@id/image` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/titleLayout` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/collectionLabel` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/title` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/excerpt` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/flexibleSpace` (`Space`): structural container for positioning children
- `@id/domain` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/timeToRead` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/overflow` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/saveLayout` (`com.pocket.ui.view.item.SaveButton`): interactive element the host fragment/adapter wires up

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
