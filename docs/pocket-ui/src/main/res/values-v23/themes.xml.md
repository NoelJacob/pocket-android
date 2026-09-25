# pocket-ui/src/main/res/values-v23/themes.xml
## What this is
This file patches the base themes for Android 6.0 (API 23+). The `-v23` qualifier (Android loads this folder only on API 23 and newer) adds `android:windowLightStatusBar` so status-bar icons turn dark on the light theme, paired with a white status bar, while keeping the dark theme white-on-dark. 
## How it fits
It redefines the same `Theme.PktBase.Light/Dark` names from `values/themes.xml`; on API 23+ devices these definitions win, on older devices the base file applies. Downstream activities (every `Pocket` screen using `Theme.Pkt.*`) get readable status-bar icons with no code branch.
Inventory:
- Themes: `Theme.PktBase.Light`, `Theme.PktBase.Dark`.
- `android:statusBarColor` = `@color/white`.
- `android:windowContentOverlay` = `@null`.
- `android:windowLightStatusBar` = `true`.
- `android:statusBarColor` = `@color/pkt_dm_base_bg`.
- `android:windowContentOverlay` = `@null`.
- `android:windowLightStatusBar` = `false`.
