# utils-android/src/main/AndroidManifest.xml

## What this is

This is the library manifest for utils-android: it declares the Android components and permissions this module contributes, merged into the app manifest at build time. Library manifests are usually minimal on purpose.

## How it fits

The app module's manifest merger picks it up automatically via the Gradle dependency; MergeManifest build tooling inspects the merged result.

## Key pieces

- (Small file with no top-level declarations matched; read it directly — it is likely constants, aliases, or wiring.)

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.
