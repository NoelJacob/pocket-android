# Pocket/src/main/res/layout/view_empty_list.xml

## What this is

This layout is the generic empty-list placeholder (art plus message slot).

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `EmptyListView` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewEmptyListBinding` class wires views to code).

Reuses shared chunks: `view_empty_list_signed_out.xml`, `view_empty_list_all.xml`, `view_empty_list_favorites.xml`, `view_empty_list_tagged.xml`, `view_empty_list_specific_tag.xml`, `view_empty_list_highlights.xml`, `view_empty_list_archive.xml`, `view_empty_list_search.xml`.

## Key pieces

- `@id/signedOut` (`include`): structural container for positioning children
- `@id/all` (`include`): structural container for positioning children
- `@id/favorite` (`include`): structural container for positioning children
- `@id/tagged` (`include`): structural container for positioning children
- `@id/specificTag` (`include`): structural container for positioning children
- `@id/highlights` (`include`): structural container for positioning children
- `@id/archive` (`include`): structural container for positioning children
- `@id/search` (`include`): structural container for positioning children

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
