# utils/src/main/java/com/pocket/util/java/JsonUtil.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `JsonUtil` (class, line 30) — core type of this file; callers reference it by name.
- `EqualsFlag` (enum, line 463) — core type of this file; callers reference it by name.
- `getValueAsInt` (fun, line 35) — entry point other code calls; see callers for context.
- `getValueAsLong` (fun, line 43) — entry point other code calls; see callers for context.
- `getValueAsText` (fun, line 51) — entry point other code calls; see callers for context.
- `getValueAsBoolean` (fun, line 59) — entry point other code calls; see callers for context.
- `getValueAsDouble` (fun, line 67) — entry point other code calls; see callers for context.
- `getValueAsBooleanSafe` (fun, line 83) — Gets the value as boolean, handling the case where it is "true"/"false" or "0"/"1".
- `getValueAsBooleanSafe` (fun, line 96) — entry point other code calls; see callers for context.
- `getValueAsInt` (fun, line 118) — entry point other code calls; see callers for context.
- `getValueAsLong` (fun, line 126) — entry point other code calls; see callers for context.
- `getValueAsText` (fun, line 134) — entry point other code calls; see callers for context.

## Junior notes

- Uses Jackson JSON trees (`ObjectNode`).
