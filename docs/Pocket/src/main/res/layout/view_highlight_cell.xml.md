# Pocket/src/main/res/layout/view_highlight_cell.xml

## What this is

This layout is one highlight row: quoted text plus note affordance.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `HighlightsAdapter` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewHighlightCellBinding` class wires views to code).

Included or previewed by: `fragment_highlights_bottom_sheet.xml`.

## Key pieces

- `@id/bar` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/highlightText` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/shareButton` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/deleteButton` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
