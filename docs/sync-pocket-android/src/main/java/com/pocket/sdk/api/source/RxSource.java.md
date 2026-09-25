# sync-pocket-android/src/main/java/com/pocket/sdk/api/source/RxSource.java

## What this is

RxJava conveniences for any Source: subscribe/bind helpers returning Rx streams so Rx-based app code can observe Things and drive syncs without hand-writing Subscriber boilerplate. RxJava models async work as Observable event streams; this adapts the engine's callback/subscription world into that shape (see also RxSync.toObservable for PendingResult adaptation). It works against the Source interface, not one implementation, so fakes stay compatible.

## How it fits

Used by Rx-based ViewModels/repositories observing Pocket data; coroutine-based code prefers the await() helpers instead. Disposal semantics mirror abandon()/stop() underneath.

## Key pieces

- `subscribe/bind Rx helpers` — stream-returning observation of Things over any Source

## Junior notes

- Disposing the stream must stop the underlying subscription: verify the chain propagates disposal if you extend these helpers.
