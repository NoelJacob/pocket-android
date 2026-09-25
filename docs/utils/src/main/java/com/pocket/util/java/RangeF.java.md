# utils/src/main/java/com/pocket/util/java/RangeF.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `RangeF` (class, line 3) — core type of this file; callers reference it by name.
- `Constrain` (enum, line 5) — core type of this file; callers reference it by name.
- `percentOf` (fun, line 20) — entry point other code calls; see callers for context.
- `valueOf` (fun, line 24) — entry point other code calls; see callers for context.
- `percentOf` (fun, line 28) — entry point other code calls; see callers for context.
- `percentOf` (fun, line 38) — entry point other code calls; see callers for context.
- `valueOf` (fun, line 48) — entry point other code calls; see callers for context.
- `constrain` (fun, line 58) — entry point other code calls; see callers for context.

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.
