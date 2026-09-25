# buildSrc/src/main/kotlin/tasks/MergeManifest.kt

## What this is

This is a custom Gradle task used by the build: a small piece of build automation (codegen, manifest merging, jar packaging) that runs during compilation rather than at app runtime.

## How it fits

It is registered from a module's `build.gradle.kts` (sync-pocket registers the Sync* codegen tasks) and wired into compilation with `dependsOn`, so generated sources exist before Kotlin compiles.

## Key pieces

- `Project` (fun, line 7) — entry point other code calls; see callers for context.

## Junior notes

- BuildSrc code runs at Gradle configuration time: a mistake here breaks every module's build, so change it carefully and sync after editing.
