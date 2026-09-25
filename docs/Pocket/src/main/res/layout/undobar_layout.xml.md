# Pocket/src/main/res/layout/undobar_layout.xml

## What this is

This layout is the legacy Undo bar (KitKat-era styling) confirming a just-taken action with an Undo button.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `UndoBarViewController` (databinding = XML layouts bound to ViewModel fields, so the generated `UndobarLayoutBinding` class wires views to code).

## Key pieces

- `@id/container_layout` (`LinearLayout`): structural container for positioning children
- `@id/undobar` (`LinearLayout`): structural container for positioning children
- `@id/regular_layout` (`LinearLayout`): structural container for positioning children
- `@id/undobar_message` (`TextView`): content region updated by the host
- `@id/divider` (`View`): structural container for positioning children
- `@id/undobar_button` (`Button`): interactive element the host fragment/adapter wires up
- `@id/confimation_textView` (`TextView`): content region updated by the host

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
