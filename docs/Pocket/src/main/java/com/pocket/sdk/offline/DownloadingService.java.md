# Pocket/src/main/java/com/pocket/sdk/offline/DownloadingService.java

## What this is
An Android `Service` (a background component that keeps the app process alive) that shows a progress notification while offline downloads run. It does no downloading itself; it only observes `OfflineDownloading` and starts, updates, or stops a foreground notification. It is wired up once via `initialize()` and then driven through listener callbacks.

## How it fits
`OfflineDownloading` does the real work (triggered by sync in `AppSync` or by opening an item) and fires `OnDownloadStateChangedListener` events. `DownloadingService` listens, calls `startForeground()` on first progress so the OS does not kill the download, then posts later count updates through `NotificationManagerCompat`. The notification text uses `SystemNotifications` channels and opens the app via a `PendingIntent` (a token the notification uses to launch an activity later).

## Key pieces
- `initialize(Context, AppThreads, OfflineDownloading, SystemNotifications)` — one-time static setup; registers the session listener that drives everything. WHY: the service is passive, so something must connect it to the download state.
- `onStartCommand()` / intent actions — handles start/update/stop commands and the user tapping cancel. WHY: extracts progress counts from intents on older paths and routes them to the cached builder.
- Notification builder + `Phrase` caching — reuses the builder and formatted string across updates. WHY: progress updates fire often, so this avoids repeated resource loads and allocations.
- `ForegroundServiceStartNotAllowedException` handling — guards the Android 12+ rule that background starts cannot launch foreground services. WHY: a save synced from another device may trigger downloads while the app is in the background, where the start is illegal.

## Junior notes
- A foreground service = a service with a visible notification that tells Android "the user knows work is happening", which raises process priority. You MUST call `startForeground()` quickly after `startForegroundService()` or the OS crashes the app.
- The file's own refactor note says the design is backwards (worker does not own the service) and the modern fix would be `WorkManager` (Android's scheduler for deferrable background jobs). Do not refactor this unprompted.
- Notification updates after the first one go through the notification manager, not `startForeground()`, to save overhead.
