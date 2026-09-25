# gradle/libs.versions.toml

## What this is
This is the Gradle version catalog: the single file where every dependency and plugin version for the whole repo is declared. It has a `[versions]` block (shared version numbers like Kotlin, OkHttp, Dagger) and a `[libraries]`/`[plugins]`-style block mapping short aliases (e.g. `androidx-paging`, `dagger-hilt`, `sentry-bom`) to Maven coordinates. Build files then reference aliases (`libs.okhttp`) instead of hardcoding strings, so a version bump is one line here rather than a hunt across modules.

## How it fits
Every module's `build.gradle.kts` (`Pocket`, `sync-pocket`, `pocket-ui`, …) resolves its third-party code through these aliases, and Renovate watches this file to open automated dependency-update PRs. The `pre-commit.sh` hook keeps the entries sorted via `sort-version-catalog.main.kts`, and `renovate.json5` tunes how updates to this file are merged (minor/patch auto-merge, majors left for humans). Modernization work proceeds group by group here (AndroidX+Compose, OkHttp, Hilt, test libs), rebuilding the app after each group.

## Key pieces
- **`[versions]` (kotlin, okhttp, okio, dagger, AndroidX sets, appcenter, aboutlibraries)** — the numbers everything else references; bumping one line moves every module using that stack together.
- **AndroidX / Compose / Paging / Navigation aliases** — the UI foundation: activity, fragment, browser (Custom Tabs), navigation, paging, work-manager, and the Compose BOM (bill of materials — a curated set of mutually compatible Compose versions) plus Material3.
- **Networking aliases (`okhttp`, `logging-interceptor`, `mockwebserver`, `okio`)** — the sync transport and its tests; `mockwebserver` is what `PocketRemoteSourceShould` uses to prove wire shapes without a network.
- **DI aliases (`dagger-hilt`, `hilt-compiler`, Hilt Gradle plugin)** — Hilt is the dependency-injection framework (constructor parameters provided automatically); the compiler generates the wiring.
- **Vendor SDK aliases (braze, adjust, appcenter, firebase-messaging, billing, sentry, snowplow)** — the corporate-extra surface: each removal deletes the alias here plus its usages and Gradle wiring.
- **Test aliases (`robolectric`, `turbine`, `kotlinx-coroutines-test`, `kotlin-junit`)** — the unit-test stack for `sync-pocket` and app ViewModel tests.
- **Plugin aliases (`plugin-android` AGP, `plugin-kotlin*`, `plugin-licensee`, `plugin-sentry`, `plugin-versions`, `plugin-aboutlibraries`)** — build-time behavior from the Android Gradle Plugin version down to license checking and dependency-report tasks.

## Junior notes
- A BOM (bill of materials) is a special dependency that only pins versions for a family — you still declare each library, but without repeating version numbers.
- `version.ref` means "reuse the version defined above"; a literal `version = "…"` means that entry owns its number — prefer refs for anything shared.
