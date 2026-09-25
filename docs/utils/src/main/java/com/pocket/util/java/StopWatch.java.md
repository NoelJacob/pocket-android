# utils/src/main/java/com/pocket/util/java/StopWatch.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `StopWatch` (class, line 9) — core type of this file; callers reference it by name.
- `warmup` (fun, line 22) — entry point other code calls; see callers for context.
- `resume` (fun, line 28) — entry point other code calls; see callers for context.
- `pause` (fun, line 35) — entry point other code calls; see callers for context.
- `addLap` (fun, line 55) — Manually record a lap in nanoseconds.
- `merge` (fun, line 72) — Combine results.
- `length` (fun, line 81) — entry point other code calls; see callers for context.
- `lengthNanos` (fun, line 85) — entry point other code calls; see callers for context.
- `intervals` (fun, line 93) — entry point other code calls; see callers for context.
- `avg` (fun, line 97) — entry point other code calls; see callers for context.
- `avgNanos` (fun, line 101) — entry point other code calls; see callers for context.

## Junior notes

- Thread-safety is explicit here (`synchronized`/`volatile`): do not call these paths from the main thread and do not add unsynchronized mutable state.
