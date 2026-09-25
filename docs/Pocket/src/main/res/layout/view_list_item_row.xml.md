# Pocket/src/main/res/layout/view_list_item_row.xml

## What this is

This layout is one row of the Saves list: thumbnail, title, excerpt, reading-time, and overflow affordance.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `BulkEditListItemAnimator`, `MyListAdapter` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewListItemRowBinding` class wires views to code).

Included or previewed by: `frag_my_list.xml`.

## Key pieces

- `@id/centerGuideline` (`View`): structural container for positioning children
- `@id/leftSwipeImage` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/rightSwipeImage` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/swipeLayout` (`com.pocket.ui.view.themed.ThemedSwipeConstraintLayout`): structural container for positioning children
- `@id/metaLayout` (`androidx.constraintlayout.widget.ConstraintLayout`): structural container for positioning children
- `@id/title` (`com.pocket.ui.view.checkable.CheckableTextView`): content region updated by the host
- `@id/domain` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/timeEstimate` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/excerpt` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/badgesLayout` (`com.pocket.ui.view.badge.BadgeLayout`): structural container for positioning children
- ...plus 9 more ids (dividers, spacers, constraints).

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
