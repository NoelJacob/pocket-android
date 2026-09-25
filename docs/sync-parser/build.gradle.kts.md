# sync-parser/build.gradle.kts

## What this is

A Gradle build script (Kotlin DSL): declares this module's plugins, dependencies, and build wiring. Gradle is the build tool; the .kts scripts configure what each module compiles against and which codegen tasks run.

## How it fits

Applied by the Gradle build when assembling this module; dependency changes here affect compilation of everything in the module. Keep version bumps in step with the version catalog where one is used.

## Entries

- Plugins: fileCollection, mavenCentral, org.jetbrains.kotlin.multiplatform, setDefaultConfigs
- Key dependencies: libs.okio; "com.apollographql.apollo3:apollo-ast:3.8.6"; kotlin("test-common"; kotlin("test-annotations-common"; libs.okio.fakefilesystem; kotlin("stdlib-jdk8"; kotlin("test"; kotlin("test-junit"
