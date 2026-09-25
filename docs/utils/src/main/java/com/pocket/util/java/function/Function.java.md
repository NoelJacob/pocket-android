# utils/src/main/java/com/pocket/util/java/function/Function.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `Function` (interface, line 10) — A functional interface that takes a value and returns another value, possibly with a
- `apply` (fun, line 16) — Apply some calculation to the input value and return some other value.

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.
