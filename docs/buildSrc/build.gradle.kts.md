# buildSrc/build.gradle.kts

## What this is

This is a Gradle build script: it declares how this module compiles, what it depends on, and which convention plugins configure it. It runs at build time and ships nothing itself.

## How it fits

The convention plugins from buildSrc do the heavy lifting; this file adds module-specific wiring (sourcesets, codegen task registration, api/test dependencies) on top.

## Key pieces

Build setup declared here: `dependencies`, `exclusiveContent`, `filter`, `forRepository`, `google`, `gradlePluginPortal`, `implementation`, `includeGroupByRegex`, `mavenCentral`, `repositories`.

- Read the file directly (it is short): the `plugins {}` block shows what it applies, the body shows what it configures.

## Junior notes

- Uses Hilt/Dagger dependency injection (`@Inject` constructor parameters provided automatically).
- Uses AndroidX platform APIs.
- BuildSrc code runs at Gradle configuration time: a mistake here breaks every module's build, so change it carefully and sync after editing.
