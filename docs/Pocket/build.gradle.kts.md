# Pocket/build.gradle.kts

## What this is

This is the Gradle build script for the `Pocket` app module. It declares the app's identity (`com.ideashower.readitlater.pro`, version `8.33.0.0`), its single F-Droid product flavor, its debug/unsignedRelease build types, and every library the app compiles against (Compose, Hilt DI, Firebase messaging, WorkManager, Markwon, RxAndroid, JSoup, and more).

## How it fits

Android Studio and CI read this file to assemble the APK: `defaultConfig` injects secrets and version info into `BuildConfig` via `buildStringField`/`buildBooleanField`, `productFlavors`/`buildTypes` select per-variant behavior, and the `dependencies` block wires in the `sync-pocket-android` and `pocket-ui` modules plus third-party SDKs. Its outputs feed `Pocket/src/main/AndroidManifest.xml` (package, version) and the merged manifest under `Pocket/merged_manifests`.

## Key pieces

- **Version fields (`versionMajor/Minor/Patch/Build` -> `versionCode`/`versionName`)** — why they exist: Play requires a monotonically increasing integer code plus a human string, so `8.33.0.0` encodes to `83300000` via the `MMMmmppbbb` formula. Bump these to ship.
- **`defaultConfig` secret fields (`GIT_SHA`, `API_KEY_PHONE`, `API_KEY_TABLET`, `API_DEV_SUFFIX`, `UA_PM`, `I_B`)** — why they exist: build-time values (API keys come from `getSecret()`) baked into `BuildConfig` so code can read them without checking secrets into source.
- **`productFlavors` (single `FDROID` flavor)** — why it exists: the F-Droid distribution needs its own market key and dev-server suffix; F-Droid signs the APK itself, so no signing config lives here.
- **`buildTypes` (`DEBUG`, `UNSIGNED_RELEASE`)** — why they exist: debug skips minification for speed, while unsignedRelease enables ProGuard (`proguard-rules.txt`) with `signingConfig = null` so CI produces an unsigned, shrunken APK for later signing.
- **`dependencies` block** — why it exists: declares the app's two in-repo modules (`sync-pocket-android`, `pocket-ui`) and shared stacks (Compose BOM, Hilt DI which auto-provides constructor params, Navigation, Paging, WorkManager, Firebase messaging, Markwon for markdown, LeakCanary for debug). Add a library here to use it in `com.pocket.app`.
- **`licensee` / `aboutLibraries` plugins** — why they exist: enforce allow-listed open-source licenses and generate the open-source-attributions screen.

## Junior notes

- **`buildStringField` creates constants, not resources.** Read them as `BuildConfig.API_KEY_PHONE` in Kotlin, not `R.string.*`.
- **`resourceConfigurations` trims locales.** Only the ~16 listed languages are packaged; adding a translation outside that list silently ships nothing until you add it here.
- **Never put real secrets in this file.** Keys come from `getSecret()` (local/CI secret store); this file only names which key to fetch.
