# utils-android/src/main/java/com/pocket/util/android/AndroidExtensions.kt

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `Context` (fun, line 13) — entry point other code calls; see callers for context.
- `stringArg` (fun, line 29) — entry point other code calls; see callers for context.

## Junior notes

- Uses AndroidX platform APIs.
