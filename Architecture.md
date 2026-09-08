# Pocket Android — Analytics-Stripped PoC (`just-app`)

## What this is
Local-build PoC: all analytics/telemetry event plumbing removed. Builds
`Pocket:assembleDevelopDebug` with **zero secrets** (no `secrets/secret.properties`, no env keys).

Build: `./gradlew clean Pocket:assembleDevelopDebug --console=plain`
Output: `Pocket/build/outputs/apk/develop/debug/Pocket-develop-debug.apk`
(`com.ideashower.readitlater.pro.dev`, 8.33.0.0). Verified clean-tree green.

## What was removed
- Top-level `analytics/` module (Snowplow `Tracker`, `UiEntityable`/`Engageable` APIs,
  appevents, entities) + `settings.gradle.kts` include + `projects.analytics` deps.
- `Pocket/src/main/java/com/pocket/analytics/` telemetry impls (`PocketTracker`,
  `ContentOpenTracker`, `BrowserAnalytics`, `ViewableImpressionScrollListener`,
  `ImpressionableInfoPageAdapter`) and `SaveExtensionAnalytics`.
- Tracker DI: `providePocketTracker` provider, `App.tracker()`/`PocketApp.tracker()`,
  all `@Inject tracker` fields, `app().tracker().*` / `tracker.*` call statements,
  `ViewableImpressionScrollListener` wiring in adapters/fragments,
  `ImpressionableInfoPageAdapter` usages (replaced with plain `InfoPageAdapter`).
- Analytics view interfaces in `pocket-ui`: `UiEntityable`/`Engageable` impls, helpers,
  `entityType`/`engageable` ctor params, `setUiEntity*`/`getEngagement*` methods.
  Public view APIs unchanged otherwise.
- `onItemViewed` telemetry chain (interface method + empty overrides + impression
  callback) where the only consumer was impression tracking.

## What was kept / faked locally
- `Project.getSecret()` returns `""` when secrets are absent (release signing never
  built here); debug build type uses the AGP debug keystore (no `TEAM` signingConfig).
- `res/font/graphik.xml` points at system `sans-serif` (XML only); `Fonts.get()` maps Graphik/Blanco/Doyle to system sans/serif at runtime (`pocket_icons.ttf` still loads from assets). Verified boot on emulator API 36. (proprietary fonts are
  secrets-gated). Cosmetic only.
- Premium forced unlocked for local builds (`// ponytail:` markers):
  `LoginInfo.hasPremium() = true`, `PocketCache.hasPremium/hasFeature = true`,
  `isPremiumUpgradeAvailable = false`.
- `SavesTab` (was `analytics.appevents.SavesTab`, but drives list-tab behavior):
  recreated as `com.pocket.app.list.SavesTab` with identical values.
- `willShowChooser` simplified to `preferredBrowserPackageName == null`
  (dropped telemetry-derived default-browser check).

## Not in scope (follow-ups)
- Vendor SDK wrappers still wired: Braze (`BrazeManager`), Sentry (`SentryManager`),
  Adjust (`AdjustSdkComponent`), FCM/push, install-referrer, billing. Stripping them
  is a separate cascade (App/PocketModule/Manifest/gradle deps).
- `PremiumAnalytics` (self-contained, sdk2-based) still constructed in
  `PremiumPurchaseFragment`; its tracking calls were removed.
- `// TODO(notes): tracker.track(...)` comments left in `MyListViewModel` as notes.

## Phase 2 — local backend + e2e (verified on emulator-5554, API 36)

- `pocket-local-server/` (Express, `node --watch server.js`, host 0.0.0.0:8080):
  `v3/guid`, `v3/send_guid`, `v3/send`, `v3/get`, `v3/fetch`, `v3/getAfterLogin`,
  `/parser`, `/graphql`. Wire shapes mirror `sync-pocket` unit tests and `V3Source` docs.
- App points at it in source (debug-gated via BuildConfig.DEBUG; release keeps production URLs) (`PocketServer.API_PRODUCTION`,
  `V3Source.PRODUCTION_SERVER`, ClientApi graphql URL, parser default all local);
  BetaConfig UI selector still works. No secrets, cleartext allowed by base config.
- Proven loop: fake login via `pocket://auth` deep link → share Wikipedia URL →
  `v3/send` add → list sync shows card → tap → ArticleView renders parsed HTML.
- Schema sources, strongest first: `sync-pocket/src/main/graphql/*.graphqls`
  (v3/parser/local/adzerk/snowplow), generated model classes under
  `sync-pocket/build/generated/`, `V3Source.java` protocol docs, unit tests.
