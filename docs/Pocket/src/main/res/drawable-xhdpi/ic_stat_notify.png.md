# Pocket/src/main/res/drawable-xhdpi/ic_stat_notify.png

## What this is

Bitmap artwork (PNG): Small status-bar notification icon, also wired as the push notification icon. This file is the `xhdpi` variant: Android picks the closest density/night/size qualifier at runtime, so the same `@drawable/ic_stat_notify` reference resolves here on matching devices. Binary asset: role and provenance only, never contents.

## How it fits

Referenced as `@drawable/ic_stat_notify`; used by SystemNotifications.kt, DownloadingService.java, ListenNotification.java. Checked-in binary with no vector source in the repo; to change the art, replace every density variant.
