# build.gradle.kts

## What this is
This is the root Gradle build file: three small blocks that set repository order and wrapper behavior for every module. The `versions()` plugin (dependency-update reporting) is applied, an `allprojects` block forces Google-first repository resolution with an exclusive-content filter for Android/Google artifacts, and the `tasks.wrapper` block pins the wrapper to the full (`ALL`) Gradle distribution. There are no dependencies here — module build files own those.

## How it fits
Every `:Pocket`, `:sync-*`, `:utils-*`, and `:project-tools` build resolves its libraries through the repository order defined here, so a compromised or stale Maven Central mirror cannot shadow an AndroidX artifact (the `exclusiveContent` filter guarantees Google groups come from `google()`). The catalog in `gradle/libs.versions.toml` names the versions; this file names where the bytes come from. Run Bastiat-style checks (`./gradlew Pocket:assembleFroidDebug`) from the root to exercise it.

## Key pieces
- **`versions()` plugin** — adds the `dependencyUpdates` report task used to survey stale dependencies; reporting only, never auto-upgrades.
- **`exclusiveContent { forRepository { google() } … }` with `includeGroupByRegex` filters** — exists so `androidx.*`, `com.android.*`, and Google/Firebase/testing-platform groups resolve exclusively from Google's Maven, closing substitution attacks and cache confusion.
- **`mavenCentral()` fallback** — exists for everything else (Kotlin, OkHttp, picocli, test libs).
- **`tasks.wrapper { distributionType = ALL }`** — exists so IDE sync and debugging get Gradle sources alongside the runtime.

## Junior notes
- `allprojects` affects the root plus every included module from `settings.gradle.kts` — prefer it here over repeating `repositories` per module.
- Repository order matters: Gradle checks them top-down, so Google-first is both a correctness and a speed choice.
