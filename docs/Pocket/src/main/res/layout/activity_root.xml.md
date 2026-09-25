# Pocket/src/main/res/layout/activity_root.xml

## What this is

This layout is the app root container used at startup before the main navigation graph takes over.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `AbsPocketActivity` (databinding = XML layouts bound to ViewModel fields, so the generated `ActivityRootBinding` class wires views to code).

## Key pieces

- `@id/pocket_root` (`com.pocket.sdk.util.PocketActivityRootView`): structural container for positioning children

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
