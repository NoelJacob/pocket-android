# Pocket/src/main/res/layout/view_home_recent_save_card.xml

## What this is

This layout is one card in the Recent Saves strip: thumbnail plus title of a recently saved item.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `RecentSavesAdapter` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewHomeRecentSaveCardBinding` class wires views to code).

Included or previewed by: `view_home_recent_saves.xml`.

## Key pieces

- `@id/titleAndImageLayout` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/image` (`com.pocket.ui.view.item.ItemThumbnailView`): structural container for positioning children
- `@id/collectionLabel` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/title` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/domain` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/timeToRead` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/marginBottom` (`Space`): structural container for positioning children
- `@id/overflow` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/favoriteIcon` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
