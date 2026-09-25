# Pocket/src/main/java/com/pocket/util/java/SimpleCallback.java
## What this is
A single-method interface, `callback()`, for "call me when done" with no result value. It is the lightest possible listener where success/failure needs no payload.
For example, `someAsyncWork(new SimpleCallback() { callback() { refreshUi(); } })` runs `refreshUi()` when the work finishes.

## How it fits
A leaf type in `com.pocket.util.java` used anywhere a fire-and-forget completion hook is needed. When a boolean outcome matters, use the sibling `SimpleResultCallback` instead.

## Key pieces
- `callback()`: the completion hook. WHY it exists: decouples "work finished" from what the finisher does next, without dragging in a framework type.

## Junior notes
- This is a Java SAM (single abstract method) interface, so Kotlin callers can pass a lambda `{ ... }` directly.
- It carries no error channel; if the operation can fail meaningfully, prefer a richer listener with success/failure methods.
