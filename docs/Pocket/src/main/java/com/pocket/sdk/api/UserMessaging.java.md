# Pocket/src/main/java/com/pocket/sdk/api/UserMessaging.java

## What this is
Shows server-driven popup messages (for example, a premium subscription expiring notice) exactly once per user. It pulls the pending message down as part of the regular sync, displays it as a dialog on the visible activity, tracks delivery/view/click analytics, and records which message ids were already seen so they never repeat. Only one UI format (custom popup) is supported; anything else is logged and skipped.

## How it fits
Singleton wired with `AppSync`, `Pocket`, and the activity monitor. At construction it adds a `since_m` flag to the shared `AppSync` `Get` request (so the server only sends new messages) and registers sync work: when a sync response contains a `userMessage`, it posts to the UI thread and calls `show(...)` on the currently visible activity. Button taps route through `act(...)` to close, open premium, or open a browser URL. The sync role is pull-only: local `seen` prefs are the fast path, the server `Get` delta supplies new messages.

## Key pieces
- `UserMessaging(...)` constructor — registers the `since_m` flag and the sync-work hook. Exists so the feature rides the shared sync instead of polling.
- `show(message, on, track)` — validates (not already seen, activity alive, supported UI id), shows a `DialogView` with up to two buttons, and returns a `UserMessageResult` reason. Exists as the single display gate; public only so internal builds can preview test messages.
- `act(action, activity)` — executes the tapped button's action: close, open premium, or open URL. Exists to translate the server's action enum into app navigation.
- `track(...)` — queues a `pv_wt` analytics action (delivered, viewed, clicked, not-shown) with the message id as context. Exists so the server can measure each message's funnel.
- `since` / `seen` prefs — `since` remembers the server cursor, `seen` records shown message ids per user. Exist to guarantee at-most-once display across restarts.

## Junior notes
- Must be called on the UI thread (it shows a dialog); the sync-work hook already switches with `runOrPostOnUiThread`.
- Unsupported `message_ui_id` values are intentionally left unmarked-as-seen so a future app version that understands them can still show them.
