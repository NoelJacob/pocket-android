# Pocket/src/main/res/layout/view_search_clear.xml

## What this is

This layout is the clear-search (X) affordance row.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `ChipEditText` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewSearchClearBinding` class wires views to code).

## Key pieces

- `@id/clear` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
