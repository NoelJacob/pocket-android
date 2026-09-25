# utils/src/main/java/com/pocket/util/java/UserFacingErrorMessage.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `UserFacingErrorMessage` (interface, line 9) — A Throwable that can explicitly provide a human friendly, user facing message.
- `getUserFacingMessage` (fun, line 10) — entry point other code calls; see callers for context.
- `find` (fun, line 12) — entry point other code calls; see callers for context.

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.
