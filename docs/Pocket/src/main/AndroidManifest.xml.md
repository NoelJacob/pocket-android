# Pocket/src/main/AndroidManifest.xml

## What this is

The app's component registry: every screen (Activity), background worker (Service), system listener (Receiver), and content bridge (Provider) the OS may launch, plus the permissions and deep-link routes that make them reachable. It is the source manifest — flavor overlays and library manifests merge on top of it to produce the installable APK's manifest.

## How it fits

The launcher, share sheets, browsers, and the OS itself discover Pocket through this file: `AppCacheCheckActivity` (+aliases) is the MAIN/LAUNCHER entry, `AddActivity` (+alias) is the share target, `PocketUrlHandlerActivity` owns `getpocket.com`/`pocket.co` App Links, and `BootReceiver`/`UpdatedReceiver` restart work after reboot/update. `com.pocket.app.App` (the `android:name` Application class) boots DI and global state before any of them run.

## Key pieces

- **Permissions (`INTERNET`, `ACCESS_NETWORK_STATE`, `ACCESS_WIFI_STATE`, `RECEIVE_BOOT_COMPLETED`, `WAKE_LOCK`, `FOREGROUND_SERVICE*`, `WRITE_EXTERNAL_STORAGE`, `POST_NOTIFICATIONS`)** — why each exists: syncing and article download need network; boot/wake/foreground-service entries keep background sync and Listen playback alive under Android 14 foreground-service-type rules; storage covers offline article/image cache on old OS versions; notifications covers push and download progress. `hardware.wifi required=false` stops the wifi permission from implying a wifi-only device filter.
- **`<queries>` (browsers, Custom Tabs, share targets, TTS)** — why it exists: Android 11+ hides other apps by default, so these intents declare what Pocket needs to see (a browser for Custom Tabs reader fallback, `SEND` text/plain targets for the custom share sheet, a TTS engine for Listen).
- **`AuthenticationActivity` + `AuthCallbackReceiverActivity` alias (`pocket://auth`)** — the login screen; the alias catches the OAuth redirect and routes it back to the singleTask auth flow. Registered by the account system (`Pocket.user()`).
- **`MainActivity` (singleTask)** — the app's home: list, discover, and navigation host. Most deep links resolve here after `PocketUrlHandlerActivity`/`AppCacheCheckActivity` triage.
- **`ItemsTaggingActivity` / `StandaloneItemsTaggingActivity`** — bulk-tag and share-sheet tag editors; the standalone variant is a translucent dialog for the add/share flow.
- **`ImageViewerActivity`** — fullscreen reader-image viewer launched from article view.
- **`PremiumPurchaseActivity` / `PremiumMessageActivity` / `PremiumSettingsActivity`** — paywall, upsell messaging, and premium settings, all parented to `MainActivity` for Up navigation.
- **`CacheSettingsActivity`, `AccountManagementActivity`, `DeleteAccountActivity`, `TCActivity`, `UnleashDebugActivity`, `ListenSettingsActivity`, `PocketUiPlaygroundActivity`** — settings sub-screens (offline cache, account, delete-account, beta flags, Listen voice) plus the internal UI playground; none are exported.
- **`PPActivity` (`:pocketsub` process, translucent)** — helper activity running in a separate process for work that must not die with the main process.
- **`ListenDeepLinkActivity`** — transparent trampoline that routes listen/audio deep links into `ListenMediaService` playback.
- **`WakefulAppService` / `DownloadingService` (dataSync)** — background sync and offline article/image downloading; the foreground-service types satisfy Android 14 restrictions.
- **`ListenMediaService` (mediaPlayback, exported) + `MediaButtonReceiver`** — Text-to-Speech "Listen" playback with headset/Bluetooth controls; exported so the OS media session can bind.
- **`AppCacheCheckActivity` + launcher aliases (`AppCacheCheckActivity`, `ClassicLogoActivity`, `BlackAndWhiteLogoActivity`, `PocketPrideActivity`)** — cold-start gate (cache check) with four swappable launcher icons, only one enabled at a time, so seasonal rebrands don't touch code.
- **`AddActivity` + alias (`SEND` text/plain, `getpocket.com/save|edit`)** — the "Save to Pocket" share target other apps send URLs to.
- **`OriginalWebOverlayActivity`** — translucent host for the original-web view overlay from reader.
- **`PocketUrlHandlerActivity` (App Links: `getpocket.com` paths, `pocket.co`, `email/clicks` trackers, `pocket://settings`)** — why it exists: verified `autoVerify` links open Pocket instead of a browser for home, saves, explore topics, collections, premium, notifications settings, and listen.
- **`CustomTabEventReceiver`, `BootReceiver` (BOOT_COMPLETED), `UpdatedReceiver` (MY_PACKAGE_REPLACED), `ShareReceiver`** — Custom Tabs callbacks, reschedule sync after reboot, migrate after app update, and record which apps users share from.
- **`FcmMessageService` (MESSAGING_EVENT)** — receives Firebase push and hands it to the notification pipeline.
- **`FileProvider` (`${applicationId}.provider`, `file_paths`)** — shares cached files (offline articles, images) with other apps via content URIs instead of world-readable paths.
- **`InitializationProvider` (WorkManager initializer removed)** — why it exists: disables auto-init so the app can start WorkManager lazily on its own schedule.

## Junior notes

- **`exported=true` means any app can start it.** Only launcher, share, deep-link, auth-callback, and media components are exported; settings and debug screens stay private.
- **Aliases are how one screen gets many doors.** The launcher icon swaps and legacy `com.ideashower.readitlater.*` names are `activity-alias` entries pointing at the real Activity — don't register the same screen twice.
- **Deep links need both manifest and verification.** The `autoVerify` intent filters only work with a matching `assetlinks.json` on the domain; test with a real link, not just the Activity launch.
