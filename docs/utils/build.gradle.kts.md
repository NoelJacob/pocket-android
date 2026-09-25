# utils/build.gradle.kts

## What this is

This is a Gradle build script: it declares how this module compiles, what it depends on, and which convention plugins configure it. It runs at build time and ships nothing itself.

## How it fits

The convention plugins from buildSrc do the heavy lifting; this file adds module-specific wiring (sourcesets, codegen task registration, api/test dependencies) on top.

## Key pieces

Build setup declared here: `api`, `dependencies`, `kotlinJvm`, `platform`.

- Read the file directly (it is short): the `plugins {}` block shows what it applies, the body shows what it configures.

## Junior notes

- Uses Kotlin coroutines (`suspend` background tasks).
- Uses Jackson JSON trees (`ObjectNode`).
- RxJava streams must be disposed/subcribed on the right scheduler; follow the existing `subscribeOn`/`observeOn` pattern in the file.
