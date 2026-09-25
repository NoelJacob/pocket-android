# buildSrc/src/main/kotlin/utils/pocket/Configs.kt

## What this is

This is build-logic support code: helpers the convention plugins and module build scripts use to keep versions, variants, and manifest handling consistent. It runs at configuration time, never in the shipped app.

## How it fits

Convention plugins and module `build.gradle.kts` files call into it (version-code math, variant filters, BuildConfig fields). `Deps.kt` and `Configs.kt` hold the versioned declarations it operates on.

## Key pieces

- `Flavors` (object, line 3) — core type of this file; callers reference it by name.
- `BuildTypes` (object, line 11) — core type of this file; callers reference it by name.
- `SigningConfigs` (object, line 17) — core type of this file; callers reference it by name.
- `FlavorDimensions` (object, line 21) — core type of this file; callers reference it by name.

## Junior notes

- BuildSrc code runs at Gradle configuration time: a mistake here breaks every module's build, so change it carefully and sync after editing.
