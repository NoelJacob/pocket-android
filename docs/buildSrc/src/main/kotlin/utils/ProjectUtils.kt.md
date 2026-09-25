# buildSrc/src/main/kotlin/utils/ProjectUtils.kt

## What this is

This is build-logic support code: helpers the convention plugins and module build scripts use to keep versions, variants, and manifest handling consistent. It runs at configuration time, never in the shipped app.

## How it fits

Convention plugins and module `build.gradle.kts` files call into it (version-code math, variant filters, BuildConfig fields). `Deps.kt` and `Configs.kt` hold the versioned declarations it operates on.

## Key pieces

- `getGitSha` (fun, line 8) — entry point other code calls; see callers for context.
- `Project` (fun, line 19) — Get a secret value.

## Junior notes

- BuildSrc code runs at Gradle configuration time: a mistake here breaks every module's build, so change it carefully and sync after editing.
