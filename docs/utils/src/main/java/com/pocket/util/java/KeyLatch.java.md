# utils/src/main/java/com/pocket/util/java/KeyLatch.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `KeyLatch` (class, line 21) — A synchronization aid that allows one or more threads to wait until a set of operations being performed in other threads completes.
- `State` (enum, line 29) — core type of this file; callers reference it by name.
- `CheckIn` (interface, line 137) — core type of this file; callers reference it by name.
- `hold` (fun, line 54) — Add an additional hold to keep the latch waiting.
- `release` (fun, line 66) — Release a hold. If there are no more holds, the latch will open.
- `strictError` (fun, line 74) — entry point other code calls; see callers for context.
- `activate` (fun, line 78) — entry point other code calls; see callers for context.
- `invalidate` (fun, line 82) — entry point other code calls; see callers for context.
- `holds` (fun, line 96) — entry point other code calls; see callers for context.
- `isOpen` (fun, line 103) — entry point other code calls; see callers for context.
- `await` (fun, line 113) — Same as {@link CountDownLatch#await(long, TimeUnit)}
- `await` (fun, line 124) — Awaits until the latch is released, invoking the 'checkIn' at an interval of 'frequencyMillis' to see if it should continue to await.
- `checkin` (fun, line 145) — The requested frequency since start or the last check in has elapsed during {@link #await(long, CheckIn)}.

## Junior notes

- Thread-safety is explicit here (`synchronized`/`volatile`): do not call these paths from the main thread and do not add unsynchronized mutable state.
