# sync-pocket/src/main/java/com/pocket/sdk/PocketExtensions.kt

## What this is

PocketExtensions adds Kotlin convenience wrappers around the Java Pocket facade (suspend helpers and null-safety niceties) so Kotlin callers avoid Java-style ceremony. It changes no behavior, only call-site ergonomics.

## How it fits

Kotlin repositories import these extensions when calling Pocket; the underlying work still runs in Pocket.java.

## Key pieces

- `Pocket` (fun, line 9) — entry point other code calls; see callers for context.
- `Pocket` (fun, line 22) — entry point other code calls; see callers for context.
- `Pocket` (fun, line 35) — entry point other code calls; see callers for context.
- `Pocket` (fun, line 49) — entry point other code calls; see callers for context.

## Junior notes

- Uses Kotlin coroutines (`suspend` background tasks).
- `suspend` functions are background tasks (coroutines): call them from a coroutine scope, never by blocking the main thread.

Names you will also see here: `PocketActions`, `Action`, `Thing`.
