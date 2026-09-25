# utils/src/main/java/com/pocket/util/Stack.kt

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `Stack` (class, line 7) — A stack similar to a java stack, but it won't crash if you try to pop an empty list
- `push` (fun, line 11) — entry point other code calls; see callers for context.
- `pop` (fun, line 13) — entry point other code calls; see callers for context.
- `clear` (fun, line 21) — entry point other code calls; see callers for context.
- `peek` (fun, line 23) — entry point other code calls; see callers for context.

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.
