# Pocket/src/main/java/com/pocket/sdk/analytics/events/CacheSettingsEvents.java

## What this is
Static helpers that fire analytics events when the user views or changes the offline-cache settings screen. It exposes three prebuilt events (view, limit change, priority change) and two send methods that translate raw setting values into event strings. It sends nothing itself; it delegates to `PvWtEvent`.

## How it fits
Called by the cache/offline settings UI when the screen opens or when the user picks a new cache size or sort order. It builds on `PvWtEvent` (same package), which queues a `pv_wt` action into the `Pocket` sync pipeline so the event uploads with the next server send. Downstream the events land in the analytics pipeline as page-view/with-tracking rows for the cache-settings page.

## Key pieces
- `VIEW` — prebuilt view-page event (type 1) fired when the cache settings screen opens.
- `CHANGE_LIMIT` / `CHANGE_PRIORITY` — private prebuilt events (type 3) reused for both kinds of setting changes.
- `sendPriorityChange(int priorityKey)` — maps an `Assets.CachePriority` key (`OLDEST_FIRST` vs newest) to the strings `"oldest"`/`"newest"` and sends it as the event's page params. Exists so analytics sees a readable value instead of an int.
- `sendLimitChange(long bytes)` — converts a byte limit to whole megabytes via `BytesUtil.bytesToMb`, using `"0"` for unlimited, and sends it. Exists so the limit is reported in the unit the dashboard expects.

## Junior notes
- Analytics events here go through the normal sync queue (`pocket.sync`), not a separate tracker, so they can be delayed offline like any other action.
- `page_params` is just a free-form string slot on the event; here it carries the new setting value.
