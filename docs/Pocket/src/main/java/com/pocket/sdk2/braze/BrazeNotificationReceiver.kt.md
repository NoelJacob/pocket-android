# Pocket/src/main/java/com/pocket/sdk2/braze/BrazeNotificationReceiver.kt
## What this is
A `BroadcastReceiver` (an Android component that wakes up when a system or SDK broadcast arrives) that handles taps on Braze push notifications. If the push carries a deep link (a URL that opens a specific in-app screen), it routes the user there — via the app's `DeepLinks` parser or a fallback browser view — and records the link for app-open analytics; otherwise it defers to Braze's default routing. Received/dismissed events are currently ignored.
## How it fits
Registered for Braze push intents; fired by the system when a notification is opened. It reads `BRAZE_PUSH_DEEP_LINK_KEY` from the intent, stamps `appOpen.deepLink` (injected `AppOpen` tracker, used to attribute the app open to the push), then starts the parsed activity with `FLAG_ACTIVITY_NEW_TASK` (required when launching from a receiver, which has no screen of its own). Non-link pushes fall through to `BrazeNotificationUtils.routeUserWithNotificationOpenedIntent()`.
## Key pieces
- `onReceive(Context, Intent)` — dispatches on the three Braze actions: opened (route), received/deleted (no-op). WHY: only taps need app-side handling.
- Deep-link branch — parses with `DeepLinks.Parser.parseLinkOpenedInPocket()`, starts it, else `App.viewUrl()`. WHY: Pocket links open natively, anything else falls back to a web view.
- `setValuesForAppOpenTracking(deepLink)` — records the link on `AppOpen`. WHY: so launch analytics can say "this open came from push X".
## Junior notes
- `@AndroidEntryPoint` means Hilt injects `appOpen` — a receiver created by the system has no constructor injection, so field injection (`lateinit var`) is the only option; do not convert to constructor parameters.
- `FLAG_ACTIVITY_NEW_TASK` is mandatory here: starting an activity from outside an activity without it crashes — if you touch the routing, keep the flag.
