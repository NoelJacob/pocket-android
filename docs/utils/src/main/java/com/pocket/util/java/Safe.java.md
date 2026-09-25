# utils/src/main/java/com/pocket/util/java/Safe.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `Safe` (class, line 19) — Utilities for accessing fields that might be null or whose parent fields might not exist.
- `Get` (interface, line 70) — core type of this file; callers reference it by name.
- `value` (fun, line 22) — Returns the value or 0 if the value is null.
- `value` (fun, line 27) — Returns the value or 0 if the value is null.
- `value` (fun, line 32) — Returns the value or 0 if the value is null.
- `value` (fun, line 37) — Returns the value or 0 if the value is null.
- `value` (fun, line 42) — Returns the value or false if the value is null.
- `get` (fun, line 47) — Obtains this value or null if any exceptions were throw such as null pointers or index out of bounds or others.
- `getBoolean` (fun, line 56) — A variant of {@link #get(Get)} that will return false if the value could not be obtained.
- `getInt` (fun, line 61) — A variant of {@link #get(Get)} that will return 0 if the value could not be obtained.
- `getLong` (fun, line 66) — A variant of {@link #get(Get)} that will return 0 if the value could not be obtained.
- `get` (fun, line 72) — Do whatever is needed to obtain the value or throw an exception if it cannot be obtained.

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.
