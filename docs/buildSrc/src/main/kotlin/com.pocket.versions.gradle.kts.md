# buildSrc/src/main/kotlin/com.pocket.versions.gradle.kts

## What this is

This is a Gradle convention plugin (`com.pocket.versions`): the shared build recipe applied with one line instead of repeating Android/Kotlin setup in every module. Convention plugins are how this repo keeps 10+ modules' builds consistent.

## How it fits

Modules opt in with the plugin alias in their `build.gradle.kts` (the app module uses the application plugin; library modules use the library plugin; JVM-only modules like utils and sync-pocket use the Kotlin JVM plugin). `Plugins.kt`/`Configs.kt` define the versioned plugin declarations; `Deps.kt` holds the dependency catalog it references.

## Key pieces

- `isNonStable` (fun, line 41) — entry point other code calls; see callers for context.
- `makeVersionComparator` (fun, line 49) — entry point other code calls; see callers for context.
- `String` (fun, line 59) — entry point other code calls; see callers for context.
- `String` (fun, line 60) — entry point other code calls; see callers for context.

## Junior notes

- Uses Jackson JSON trees (`ObjectNode`).
- BuildSrc code runs at Gradle configuration time: a mistake here breaks every module's build, so change it carefully and sync after editing.
