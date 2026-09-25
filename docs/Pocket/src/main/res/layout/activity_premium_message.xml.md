# Pocket/src/main/res/layout/activity_premium_message.xml

## What this is

This layout is the full-screen Premium upsell message.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `PremiumMessageFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `ActivityPremiumMessageBinding` class wires views to code).

## Key pieces

- `@id/appbar` (`com.pocket.ui.view.AppBar`): structural container for positioning children
- `@id/header` (`ImageView`): interactive element the host fragment/adapter wires up
- `@id/image` (`ImageView`): interactive element the host fragment/adapter wires up
- `@id/message_title` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/message_text` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/button` (`com.pocket.ui.view.button.ErrorButton`): interactive element the host fragment/adapter wires up
- `@id/disclaimer` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
