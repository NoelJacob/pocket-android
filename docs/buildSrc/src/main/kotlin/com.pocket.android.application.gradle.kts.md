# buildSrc/src/main/kotlin/com.pocket.android.application.gradle.kts

## What this is

This is a Gradle convention plugin (`com.pocket.android.application`): the shared build recipe applied with one line instead of repeating Android/Kotlin setup in every module. Convention plugins are how this repo keeps 10+ modules' builds consistent.

## How it fits

Modules opt in with the plugin alias in their `build.gradle.kts` (the app module uses the application plugin; library modules use the library plugin; JVM-only modules like utils and sync-pocket use the Kotlin JVM plugin). `Plugins.kt`/`Configs.kt` define the versioned plugin declarations; `Deps.kt` holds the dependency catalog it references.

## Key pieces

Build setup declared here: `System.getenv`, `android`, `androidComponents`, `beforeVariants`, `kotlin`, `setDefaultConfigs`.

- Read the file directly (it is short): the `plugins {}` block shows what it applies, the body shows what it configures.

## Junior notes

- BuildSrc code runs at Gradle configuration time: a mistake here breaks every module's build, so change it carefully and sync after editing.
