# Pocket/src/main/java/com/pocket/sdk/util/service/BootReceiver.java
## What this is
A broadcast receiver (an Android component that wakes up on system events) that re-registers periodic background sync after the device reboots. It ignores every broadcast except boot-completed and forwards that one to each app-lifecycle observer's `onDeviceBoot()`. It holds a wake lock while running so the device does not sleep mid-dispatch.
## How it fits
Declared in the manifest for the boot-completed broadcast; on reboot the system instantiates it and calls `doOnReceive()`, which dispatches through `App.from(context).dispatcher()` to observers like `BackgroundSync` (reschedules its sync job) and `WakeLockManager`. It extends `WakeLockBroadcastReceiver`, which wraps the acquire/release around the callback.
## Key pieces
- `doOnReceive(context, intent)` — guards on `ACTION_BOOT_COMPLETED`, then dispatches `AppLifecycle::onDeviceBoot`. WHY: as an exported receiver it can receive broadcasts from any app, so it must verify the action before acting.
- `WakeLockBroadcastReceiver` base — acquires a wake lock before and releases after `doOnReceive()`. WHY: boot-time work runs with the screen off and would otherwise race the device going back to sleep.
## Junior notes
- Never trust extras on the incoming intent here; the class comment warns they can come from outside apps.
- If background sync stops working after reboot, check the manifest declaration of this receiver first; without it nothing re-registers the schedule.
