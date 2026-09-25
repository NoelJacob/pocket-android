# Pocket/src/main/java/com/pocket/sdk/notification/push/firebase/FcmMessageService.kt
## What this is
Firebase messaging entry point: receives silent push messages from the server and turns them into a sync. When background sync is set to "Instant", each server-side change sends a data-only push, and this service wakes the app to pull those changes.
## How it fits
Declared as a `FirebaseMessagingService` (an Android service Firebase starts when a message arrives, even if the app is in the background). `onMessageReceived()` checks the message data: Pinpoint marketing pushes (with `pinpoint.notification.title/body`) are ignored here, everything else triggers `app.backgroundSync().scheduleSyncFromPush()` but only when instant sync is enabled. `onNewToken()` calls `App.push().invalidate()` so `PktPush` re-registers the rotated token.
## Key pieces
- `onMessageReceived(remoteMessage)` — routes data payloads to an instant-sync schedule; the push itself carries no content, it is just a "something changed, come sync" nudge.
- `onNewToken(s)` — token rotation hook; marks push registration stale so the server gets the new token.
## Junior notes
- This is a silent-push design: the notification carries no user-visible text, so if sync does not run (e.g. instant sync disabled), nothing is shown; user-visible alerts are posted separately via `SystemNotifications`.
- The service is started by the system, not by app code; keep `onMessageReceived` fast and delegate to `backgroundSync()`, never do network I/O inline here.
