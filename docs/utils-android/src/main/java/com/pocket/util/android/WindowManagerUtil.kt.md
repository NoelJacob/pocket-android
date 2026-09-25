# utils-android/src/main/java/com/pocket/util/android/WindowManagerUtil.kt

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `WindowManagerUtil` (object, line 9) — Methods for getting screen width and height depending on API level
- `getScreenWidth` (fun, line 11) — entry point other code calls; see callers for context.
- `getScreenHeight` (fun, line 20) — entry point other code calls; see callers for context.
- `WindowManager` (fun, line 29) — entry point other code calls; see callers for context.
- `WindowManager` (fun, line 30) — entry point other code calls; see callers for context.

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.
