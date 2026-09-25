# Pocket/src/main/res/layout/activity_item_tagging.xml

## What this is

This layout is the add-tags screen: the tag editor opened for one save.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `ItemsTaggingFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `ActivityItemTaggingBinding` class wires views to code).

## Key pieces

- `@id/toolbar_layout` (`com.pocket.ui.view.themed.ThemedConstraintLayout`): structural container for positioning children
- `@id/appbar` (`com.pocket.ui.view.AppBar`): structural container for positioning children
- `@id/content` (`com.pocket.ui.view.themed.ThemedRelativeLayout`): structural container for positioning children
- `@id/edit_tags_container` (`com.pocket.util.android.view.MaxHeightScrollView`): scrollable container
- `@id/edit_tags` (`com.pocket.util.android.view.chip.ChipEditText`): interactive element the host fragment/adapter wires up
- `@id/divider` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/list` (`com.pocket.ui.view.themed.ThemedRecyclerView`): content region updated by the host
- `@id/header_fixed` (`com.pocket.ui.view.menu.SectionHeaderView`): structural container for positioning children
- `@id/progress` (`com.pocket.ui.view.progress.RainbowProgressCircleView`): structural container for positioning children
- `@id/save` (`com.pocket.ui.view.button.SubmitButton`): interactive element the host fragment/adapter wires up

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
