# Pocket/src/main/res/layout/view_empty_list_favorites.xml

## What this is

This layout is the empty Favorites empty state shown before anything is starred.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Included or previewed by: `view_empty_list.xml`.

## Key pieces

- `@id/splitGuide` (`androidx.constraintlayout.widget.Guideline`): structural container for positioning children
- `@id/image` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/title` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/icon` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
