# utils-android/src/main/java/com/pocket/util/android/AndroidLogger.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `AndroidLogger` (class, line 7) — core type of this file; callers reference it by name.
- `v` (fun, line 10) — entry point other code calls; see callers for context.
- `w` (fun, line 15) — entry point other code calls; see callers for context.
- `e` (fun, line 20) — entry point other code calls; see callers for context.
- `i` (fun, line 25) — entry point other code calls; see callers for context.
- `d` (fun, line 30) — entry point other code calls; see callers for context.
- `printStackTrace` (fun, line 35) — entry point other code calls; see callers for context.

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.

Names you will also see here: `Logs`.
