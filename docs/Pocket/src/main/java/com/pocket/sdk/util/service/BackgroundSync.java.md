# Pocket/src/main/java/com/pocket/sdk/util/service/BackgroundSync.java
## What this is
The setting and scheduler for background syncing: how often Pocket refreshes the user's list when the app is not open. Users pick instant (push-triggered), hourly, twice daily, daily, or never; timer choices are scheduled as periodic background work, while instant relies on push notifications. It is a Hilt singleton (a single app-wide instance whose constructor parameters are provided automatically) that also observes app lifecycle events.
## How it fits
Created once by Hilt dependency injection; the settings screen reads `pref()` and `getSelectedSettingLabel()`, and changes flow through `setBackgroundSyncing()`. Timer syncs run `SyncJob`, a `Worker` (Android's background-task API) that calls `AppSync.sync()` and always reports success so retries happen on the next slot. `Push` handles instant sync (`scheduleSyncFromPush()`), and `BootReceiver` re-registers the schedule after reboot via `onDeviceBoot()`.
## Key pieces
- `SYNC_INSTANT` / `SYNC_HOURLY` / `SYNC_TWICE_DAILY` / `SYNC_DAILY` / `SYNC_NEVER` — the stored choices. WHY: a single int preference drives both the label and the schedule.
- `pref()` / `getSelectedSettingLabel()` / `isTimerSync()` — read access for the settings UI. WHY: the screen shows labels without knowing scheduling details.
- `setBackgroundSyncing(value, cxt_ui)` — stores the choice and either cancels or (re)schedules the periodic job. WHY: the single choke point that keeps the preference and the real schedule in sync.
- `scheduleAlarmSync()` — maps the preference to an `AlarmManager` interval and schedules `SyncJob`, or cancels for instant/never. WHY: push covers instant, nothing covers never.
- `onLoggedIn(isNewUser)` — defaults hourly (or never when installed on an SD card), schedules, then tries to register for push and upgrades to instant on success. WHY: new logins prefer instant sync but degrade gracefully without push.
- `onLogoutStarted()` — cancels all scheduled syncs. WHY: one user's background work must never run for the next user.
- `SyncJob.doWork()` — runs a full sync in the background, swallowing exceptions into logs and returning success. WHY: a failed slot retries next interval instead of piling up backoff retries.
## Junior notes
- `SYNC_TEST_ONLY_AS_SHORT_AS_POSSIBLE` (15 minutes) exists for testing only; never expose it in the settings UI.
- Deregistering push when leaving instant mode is deliberately disabled (see the TODO); changing that would kill notification delivery, so leave it alone.
