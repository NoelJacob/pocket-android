# sync/src/main/java/com/pocket/sync/SyncExtensions.kt

## What this is

Small Kotlin helpers that make the Java-first sync engine feel idiomatic in Kotlin. A PendingResult is the engine's async callback handle (onSuccess/onFailure/abandon), and coroutines are Kotlin's way of writing background work as if it were sequential; the await() helper here bridges the two so ViewModels can just suspend until a sync finishes. The buildThing/buildAction helpers wrap the generated Java builders in trailing-lambda syntax, and the action() helper fills in one missing SyncResult.Builder overload.

## How it fits

Used anywhere Kotlin code talks to the engine: ViewModels and repositories call await() on AppSource operations instead of nesting callbacks, and feature code builds generated Things/Actions with buildThing/buildAction rather than the raw Java builder calls. It sits on top of PendingResult, SyncResult.Builder, ThingBuilder, and ActionBuilder without changing their behavior.

## Key pieces

- `PendingResult.await()` — suspends a coroutine until the async sync work completes, throwing on failure and abandoning the work if the coroutine is cancelled
- `buildThing/buildAction` — run a lambda against a generated builder then build, so construction reads as one block instead of chained setter calls
- `SyncResult.Builder.action(a, status, cause)` — convenience overload that forwards with a null resolved value so sources recording action outcomes need one less argument

## Junior notes

- invokeOnCancellation calls abandon(), so cancelling the coroutine also cancels the underlying sync work rather than leaking it.
- These are pure wrappers with no state; if a call site needs retry or timeout logic, that belongs at the call site, not here.
