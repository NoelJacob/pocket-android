# utils-android/src/main/java/com/pocket/util/android/WIP.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `WIP` (class, line 11) — A set of Dev methods that should never ever ship. These methods are only for debugging in process and should be removed after use.
- `l` (fun, line 16) — A quick logging method. Meant to be used in places where logging has no value outside of debugging an immediate issue and will be removed after the issue is fin
- `run` (fun, line 33) — entry point other code calls; see callers for context.
- `releaseLog` (fun, line 61) — entry point other code calls; see callers for context.

## Junior notes

- Uses Jackson JSON trees (`ObjectNode`).

Names you will also see here: `Logs`.
