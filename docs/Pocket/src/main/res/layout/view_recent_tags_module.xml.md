# Pocket/src/main/res/layout/view_recent_tags_module.xml

## What this is

This layout is the recently-used tags section inside the tag editor.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `RecentTagsModule` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewRecentTagsModuleBinding` class wires views to code).

## Key pieces

- `@id/header` (`com.pocket.ui.view.menu.SectionHeaderView`): structural container for positioning children
- `@id/tag1` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/divider1` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/tag2` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/divider2` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/tag3` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
