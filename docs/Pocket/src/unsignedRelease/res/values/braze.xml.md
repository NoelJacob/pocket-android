# Pocket/src/unsignedRelease/res/values/braze.xml

## What this is

The production (unsignedRelease) overlay holding the Braze push/messaging API key (`com_braze_api_key`). Its own comment says the default config lives in `src/main/res/values/braze.xml` and this file only overrides the key for prod. Note: the source file is currently deleted from the tree (no `Pocket/src/unsignedRelease` sources remain) while Braze itself is still wired up — the SDK (`com.braze:android-sdk-ui`) is a declared dependency and Braze activities/receivers still appear in the merged manifest.

## How it fits

When present, the manifest/resource merger layers this overlay onto the base Braze values so release builds initialize the Braze SDK (`com.pocket.sdk2.braze.BrazeNotificationReceiver`, `BrazeFeedActivity`/`ContentCardsActivity`) against the production key instead of a dev one. Entry inventory: one string, `com_braze_api_key` (`translatable="false"`), the prod key the Braze SDK reads at init. Treat the key as a public identifier, not a secret, but restore or re-point this file before cutting a release build that needs Braze push.
