# Pocket/src/main/res/layout/ril_root.xml

## What this is

This layout is the read-it-later (legacy list) root container.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `PocketActivityRootView` (databinding = XML layouts bound to ViewModel fields, so the generated `RilRootBinding` class wires views to code).

## Key pieces

- `@id/content` (`com.pocket.sdk.util.PocketActivityContentView`): structural container for positioning children
- `@id/stub_listen` (`ViewStub`): structural container for positioning children
- `@id/stub_lock` (`ViewStub`): structural container for positioning children

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
