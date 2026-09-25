# Pocket/src/main/java/com/pocket/sdk/notification/SystemNotifications.kt
## What this is
App-wide wrapper around Android system notifications (the shade/pull-down alerts). It hands out pre-styled `NotificationCompat.Builder`s and knows about notification channels (Android 8+ categories like "recommendations" that users can mute individually), plus whether notifications are enabled at the device level.
## How it fits
Injected singleton (Hilt DI provides it wherever needed, which means constructor parameters are supplied automatically); background sync, recommendations, and new-feature prompts call `newDefaultBuilder()` to get a builder, then post it via `NotificationManagerCompat`. On Android O+ (`Oreo` impl) it creates the `Channel` set at startup; on older devices (`PreOreo` impl) it skips channels. A daily `DeviceLevelNotificationSettingWorker` (a `Worker`, i.e. a scheduled background job) records whether the user has disabled notifications system-wide.
## Key pieces
- `newDefaultBuilder()` — returns the standard app notification builder (icon, timestamp, brand color) on the `APP` channel; every feature notification starts here for a consistent look.
- `areNotificationsEnabled()` — delegates to `NotificationManagerCompat.areNotificationsEnabled()`; the single check features use before posting.
- `Channel` — the channel catalog: `APP`, `ARTICLE_RECOMMENDATIONS`, `LEGAL_UPDATES`, `NEW_FEATURES` (plus deprecated `COMMUNICATION`, deleted on upgrade).
- `PreOreo` / `Oreo` — version split: `PreOreo` builds channel-less notifications with user sound/lights prefs; `Oreo` creates channels with per-channel importance, lights, and vibration settings.
## Junior notes
- On Android 8+, importance and sound can only be set when the channel is first created; changing code later has no effect until the app is reinstalled or the channel is deleted, so channel tweaks look like they "do nothing" on existing installs.
- The `sound`/`lights` preferences here are per-user (`prefs.forUser`); the worker checking device-level settings is scheduled on first run or after the 7.48 upgrade and then repeats daily.
