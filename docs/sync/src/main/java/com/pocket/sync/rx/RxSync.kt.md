# sync/src/main/java/com/pocket/sync/rx/RxSync.kt

## What this is

Bridges the engine's callback-style async (PendingResult) to RxJava, the reactive-streams library the app uses to model async work as subscribable event streams (an Observable emits values over time; observers react to each emission). toObservable() wraps any PendingResult so success and failure arrive as stream events, and RxSyncResult is a sealed (closed set of subclasses) Success/Failure pair describing which one arrived.

## How it fits

Used by Rx-based call sites (see RxSource in sync-pocket-android) that want to compose sync work with the rest of the app's Rx chains instead of nesting onSuccess/onFailure callbacks. Disposing the stream abandons the underlying work.

## Key pieces

- `toObservable()` — adapts a PendingResult into an Observable that emits one RxSyncResult then completes
- `RxSyncResult.Success/Failure` — the two possible outcomes, so stream operators can branch on them with getValueOr for defaults

## Junior notes

- Disposal propagates to abandon(), so dropping a subscription genuinely cancels the sync work instead of leaking it.
- New coroutine-based code should prefer the await() helper in SyncExtensions rather than adding more Rx adapters.
