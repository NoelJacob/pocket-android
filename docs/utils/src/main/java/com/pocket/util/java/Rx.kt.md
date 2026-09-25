# utils/src/main/java/com/pocket/util/java/Rx.kt

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `Disposable` (fun, line 6) — entry point other code calls; see callers for context.

## Junior notes

- Uses RxJava reactive streams (`Observable`/`Single`).
- RxJava streams must be disposed/subcribed on the right scheduler; follow the existing `subscribeOn`/`observeOn` pattern in the file.
