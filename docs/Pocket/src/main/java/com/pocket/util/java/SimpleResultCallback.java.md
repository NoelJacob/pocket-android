# Pocket/src/main/java/com/pocket/util/java/SimpleResultCallback.java
## What this is
A single-method interface, `callback(boolean result)`, for "call me with yes/no" completion. It adds a success flag to what `SimpleCallback` provides.
For example, `validator.check(new SimpleResultCallback() { callback(ok) { show(ok ? "saved" : "failed"); } })` branches the UI on the outcome.

## How it fits
A leaf type in `com.pocket.util.java` for boolean-outcome async completions. It sits beside `SimpleCallback`: no-arg when only completion matters, boolean-arg when the caller must know pass/fail.

## Key pieces
- `callback(boolean result)`: completion hook with outcome. WHY it exists: the minimal contract for branching on success without inventing a per-feature listener.

## Junior notes
- This is a Java SAM interface, so Kotlin callers can pass a lambda like `{ ok -> ... }`.
- A bare boolean cannot explain *why* something failed; for error messages or exceptions use a richer callback.
