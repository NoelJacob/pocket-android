# Pocket/merged_manifests/playUnsignedRelease/AndroidManifest.xml

## What this is

The final merged manifest for the `playUnsignedRelease` variant (version `8.33.0.0`, code `83300000`, `minSdk 23`/`targetSdk 34`), checked in as a build snapshot by the `registerCopyMergedManifestTask` Gradle task. It is generated output, not source: the merger combined `Pocket/src/main/AndroidManifest.xml`, flavor overlays, and every library manifest into the single registry the APK actually ships.

## How it fits

This is what the OS sees at install time — use it to answer "what is really in the unsignedRelease build?" when the source manifest and runtime behavior disagree. Everything in the source manifest appears here (same Activities, services, receivers, FileProvider, deep links), resolved with concrete values (`${applicationId}` becomes `com.ideashower.readitlater.pro`) plus library-contributed entries grouped below.

## Key pieces

- **Pocket screens/services/receivers** — identical to the source manifest's registry (`AuthenticationActivity` + auth alias, `MainActivity`, tagging activities, `ImageViewerActivity`, premium/settings/Listen activities, `AppCacheCheckActivity` + launcher aliases, `AddActivity` + share alias, `PocketUrlHandlerActivity` App Links, `WakefulAppService`/`DownloadingService`, `ListenMediaService` + `MediaButtonReceiver`, boot/update/share receivers, `FcmMessageService`, `FileProvider`). Why repeated here: proves the merge kept them and pins their shipped attributes.
- **Billing additions (`BILLING`, `AD_ID`, `BIND_GET_INSTALL_REFERRER_SERVICE` permissions; `ProxyBillingActivity[V2]`; billing `queries`)** — why they exist: Play Billing library requirements for `PremiumPurchaseActivity`; the Facebook/Instagram packages and Custom Tabs queries support share and in-app purchase flows.
- **Braze engagement (`BrazeNotificationReceiver`, `BrazePushReceiver`, `BrazeFlushPushDeliveryReceiver`, `BrazeWebViewActivity`, `BrazeFeedActivity`, `ContentCardsActivity`, `NotificationTrampolineActivity`)** — why they exist: Braze push + in-app content cards/News Feed backing the messaging behind `FcmMessageService`.
- **Firebase/transport (`FirebaseInitProvider`, `FirebaseMessagingService`, `FirebaseInstanceIdReceiver`, `ComponentDiscoveryService`, `TransportBackendDiscovery`, `JobInfoSchedulerService`)** — why they exist: push registration and analytics event delivery behind the app's own `FcmMessageService`.
- **WorkManager set (`SystemAlarmService`, `SystemJobService`, `SystemForegroundService`, `ConstraintProxy*`, `RescheduleReceiver`, `DiagnosticsReceiver`)** — why they exist: the background-sync scheduler's OS hooks; note the source manifest removes the auto-initializer so these start on the app's schedule.
- **Infra (`SentryInitProvider`/`SentryPerformanceProvider` with `auto-init=false`, `ProfileInstallReceiver`, Play Core split activities/services, `GoogleApiActivity`, Room `MultiInstanceInvalidationService`, `EmojiCompat`/`ProcessLifecycle`/`ProfileInstaller` startup initializers)** — why they exist: crash reporting (manually started), baseline-profile installs, and Play Asset Delivery plumbing the release needs.

## Junior notes

- **Never edit this file.** It is a snapshot; change the source manifest, library versions, or overlays and regenerate — edits here are overwritten on the next build.
- **Diff this against the source manifest to find library surprises.** Any entry you didn't write came from a dependency's manifest (Braze, Firebase, WorkManager); that diff is the fastest way to audit what a new SDK registers.
