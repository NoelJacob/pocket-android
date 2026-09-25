# sync/src/main/java/com/pocket/sync/source/PendingResult.java

## What this is

The engine's async result handle: an object representing work that has not finished yet, with onSuccess/onFailure/onComplete callbacks and an abandon() escape hatch for cancellation. The SuccessCallback/ErrorCallback/CompleteCallback nested types give each outcome its own listener shape. It is the return type of every async source operation.

## How it fits

Every AsyncSource call hands one of these back; UI code either chains callbacks directly or converts via await() (coroutines) or toObservable() (RxJava). PendingImpl is the standard implementation sources use internally.

## Key pieces

- `onSuccess/onFailure/onComplete` — the three listener registrations covering success, failure, and either-outcome cleanup
- `abandon()` — cancels the underlying work, wired to coroutine cancellation and Rx disposal by the adapters
- `SuccessCallback/ErrorCallback/CompleteCallback` — typed listener interfaces so each registration only sees its own outcome

## Junior notes

- A PendingResult nobody observes is a leak of intent: always attach a listener, await it, or explicitly abandon it.
