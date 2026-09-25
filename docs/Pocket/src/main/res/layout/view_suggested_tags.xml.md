# Pocket/src/main/res/layout/view_suggested_tags.xml

## What this is

This layout is the suggested-tags chip group offered while tagging a save.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `SuggestedTagsModule` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewSuggestedTagsBinding` class wires views to code).

## Key pieces

- `@id/suggested_tags` (`com.pocket.util.android.view.chip.ChipLayout`): structural container for positioning children
- `@id/progress` (`com.pocket.ui.view.progress.RainbowProgressCircleView`): structural container for positioning children
- `@id/error` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
