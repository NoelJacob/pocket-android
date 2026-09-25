# Pocket/src/main/java/com/pocket/app/settings/PrefsFragment.kt
## What this is
This is the main Settings screen — the long scrolling list of account, theme, reading, offline, syncing, notification, and about rows. It builds different rows for logged-in vs logged-out users, opens every sub-settings screen (Premium, Account management, App icon, Cache limits, Open-source licenses, beta config), and hosts destructive actions (logout, clear downloads) behind confirmation dialogs. It rebuilds its rows whenever it becomes visible or relevant external state changes.
## How it fits
A Hilt entry-point fragment (dependencies injected into fields) extending AbsPrefsFragment; hosted by the settings navigation graph. Rows navigate downstream: PremiumSettingsFragment.show, AccountManagementFragment.show, CacheSettingsFragment.show, findNavController to AppIconSettings/OpenSourceLicenses, CustomTabs browser picker dialog, StorageLocationPickerDialog, and external URLs via App.viewUrl. Background-sync picks delegate to OptionsDialogs; theme picks go through Theme/SystemDarkTheme.
## Key pieces
- `createPrefs(prefs)`: the whole screen in one method — account block (premium/manage/logout or sign-in), rotation-lock and share-overlay toggles, theme picker + follow-system toggle, app-icon row showing the current icon label, reading block (always-open-original, browser, previous/next, justification, auto-fullscreen, continue-reading), offline block (download toggles, user-agent, storage, cache limits, clear cache), syncing block, notifications block, about/version block.
- Theme rows: multiple-choice bound to Theme.pref() turns SystemDarkTheme off on manual pick; the follow-system toggle routes through turnOn/turnOff so analytics fire — WHY the toggle's onChange returns false (it handles the write itself).
- `lifecycleScope + repeatOnLifecycle(STARTED)`: re-checks CustomTabs browser availability and rebuilds rows each time the screen is shown.
- `onStart/onStop`: re-subscribes to storage-type changes (rebuild on SD-card swap) and stops username/premium subscriptions.
- `addAlphaSettings()` / `newOpenUrlPref()`: internal-builds-only beta entry vs one-line external-link rows.
## Junior notes
- repeatOnLifecycle relaunches its block on every START — that is why list.itemAnimator is nulled (prevents row flicker on rebuild) and why rebuildPrefs must be idempotent.
- Hilt field injection (@Inject lateinit var) happens before onViewCreated — never touch those fields in the constructor or init blocks.
- Logout and clear-cache are guarded by AlertMessaging/AlertDialog confirmations; clear runs assets.clearOfflineContent with a progress fragment dismissed on the lifecycle scope.
