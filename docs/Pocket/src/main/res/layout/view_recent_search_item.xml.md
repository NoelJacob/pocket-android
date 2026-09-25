# Pocket/src/main/res/layout/view_recent_search_item.xml

## What this is

This layout is one recent-search history row.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `RecentSearchAdapter` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewRecentSearchItemBinding` class wires views to code).

Included or previewed by: `frag_my_list.xml`.

## Key pieces

- `@id/recentSearchText` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/divider` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
