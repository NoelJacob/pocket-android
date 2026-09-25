# utils-android/src/main/java/com/pocket/util/android/WindowUtil.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `WindowUtil` (class, line 6) — core type of this file; callers reference it by name.

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.
