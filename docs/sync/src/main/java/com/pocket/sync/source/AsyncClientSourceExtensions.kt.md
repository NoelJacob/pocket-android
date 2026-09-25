# sync/src/main/java/com/pocket/sync/source/AsyncClientSourceExtensions.kt

## What this is

Adds suspending (coroutine-friendly, pausing without blocking a thread) variants of the async client operations. Instead of chaining onSuccess/onFailure callbacks, callers suspend until the work completes; internally the wrappers share one PendingResult across the proxied call sequence and return it. Coroutines are Kotlin's lightweight background tasks, and suspending functions are the ones allowed to pause.

## How it fits

Used by Kotlin ViewModels/repositories that want straight-line code (val result = source.sync(...)) instead of callback nesting. It layers SuspendingClientSource on top of AppSource/AsyncClientSource without changing engine semantics.

## Key pieces

- `SuspendingClientSource` — the coroutine facade whose methods mirror the async client calls but suspend for their results

## Junior notes

- Suspension is not cancellation-proof by itself: cancelling the coroutine abandons the shared PendingResult, which is the desired cleanup path.
