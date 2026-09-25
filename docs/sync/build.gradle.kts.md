# sync/build.gradle.kts

## What this is

A Gradle build script (Kotlin DSL): declares this module's plugins, dependencies, and build wiring. Gradle is the build tool; the .kts scripts configure what each module compiles against and which codegen tasks run.

## How it fits

Applied by the Gradle build when assembling this module; dependency changes here affect compilation of everything in the module. Keep version bumps in step with the version catalog where one is used.

## Entries

- Plugins: kotlinJvm
- Key dependencies: projects.utils; Deps.Google.Guava.guava; libs.okio; platform(libs.kotlinx.coroutines.bom; libs.kotlinx.coroutines.core
