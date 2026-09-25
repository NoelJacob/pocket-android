# Pocket/src/main/res/layout/view_pref_cache_limit.xml

## What this is

This layout is the offline-cache-limit preference row (download caps for saved articles).

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `CacheLimitPreferenceView` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewPrefCacheLimitBinding` class wires views to code).

## Key pieces

- `@id/set_limit_desc` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/current_cache_limit_textview` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/limit_to` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/value_limit` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/seekbar` (`com.pocket.app.settings.cache.CacheLimitSeekbar`): structural container for positioning children
- `@id/current_items_cached_textview` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
