# Pocket/src/main/res/layout/view_tooltip_v3.xml

## What this is

This layout is the anchored feature tooltip popup (title, body, dismiss).

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `CaretTooltip` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewTooltipV3Binding` class wires views to code).

## Key pieces

- `@id/text` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/button` (`com.pocket.ui.view.button.OnColorButton`): interactive element the host fragment/adapter wires up

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
