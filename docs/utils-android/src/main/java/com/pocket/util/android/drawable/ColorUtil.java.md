# utils-android/src/main/java/com/pocket/util/android/drawable/ColorUtil.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `ColorUtil` (class, line 5) — core type of this file; callers reference it by name.
- `gray` (fun, line 13) — Same as {@code Color#argb(alpha, b, b, b)}
- `to255` (fun, line 22) — Convert [0...1] to [0...255]
- `setAlpha` (fun, line 29) — Returns the color with the alpha channel set to the provided value.
- `setAlpha` (fun, line 36) — Returns the color with the alpha channel set to the provided value.
- `toString` (fun, line 40) — entry point other code calls; see callers for context.

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.
