# buildSrc/src/main/kotlin/tasks/SyncGen.kt

## What this is

This is a custom Gradle task used by the build: a small piece of build automation (codegen, manifest merging, jar packaging) that runs during compilation rather than at app runtime.

## How it fits

It is registered from a module's `build.gradle.kts` (sync-pocket registers the Sync* codegen tasks) and wired into compilation with `dependsOn`, so generated sources exist before Kotlin compiles.

## Key pieces

- `TaskNames` (object, line 8) — core type of this file; callers reference it by name.
- `Project` (fun, line 19) — Moves jar generated above to the correct directory
- `Project` (fun, line 30) — Moves jar generated above to the correct directory
- `Project` (fun, line 41) — Creates example generated classes and places them in /sync-gen/examples

## Junior notes

- BuildSrc code runs at Gradle configuration time: a mistake here breaks every module's build, so change it carefully and sync after editing.

Names you will also see here: `ExamplesGenerator`.
