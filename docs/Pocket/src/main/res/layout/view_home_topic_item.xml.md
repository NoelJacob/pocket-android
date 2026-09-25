# Pocket/src/main/res/layout/view_home_topic_item.xml

## What this is

This layout is one topic chip/row in the Home topics list.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `TopicsAdapter` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewHomeTopicItemBinding` class wires views to code).

Included or previewed by: `fragment_home.xml`.

## Key pieces

- `@id/topicTitle` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/chevron` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
