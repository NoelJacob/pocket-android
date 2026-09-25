# utils-android/src/main/java/com/pocket/util/android/AndroidBgThreadFactory.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `AndroidBgThreadFactory` (class, line 10) — A thread factory that ensures it runs with the recommended background thread priority in Android
- `wrap` (fun, line 12) — entry point other code calls; see callers for context.
- `newThread` (fun, line 35) — entry point other code calls; see callers for context.
- `run` (fun, line 38) — entry point other code calls; see callers for context.

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.
