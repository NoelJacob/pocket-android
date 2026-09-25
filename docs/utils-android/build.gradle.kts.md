# utils-android/build.gradle.kts

## What this is

This is a Gradle build script: it declares how this module compiles, what it depends on, and which convention plugins configure it. It runs at build time and ships nothing itself.

## How it fits

The convention plugins from buildSrc do the heavy lifting; this file adds module-specific wiring (sourcesets, codegen task registration, api/test dependencies) on top.

## Key pieces

Build setup declared here: `android`, `api`, `dependencies`, `platform`, `pocketAndroidLib`.

- Read the file directly (it is short): the `plugins {}` block shows what it applies, the body shows what it configures.

## Junior notes

- Uses Kotlin coroutines (`suspend` background tasks).
- Uses AndroidX platform APIs.
