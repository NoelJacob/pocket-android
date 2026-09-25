# sync/src/main/java/com/pocket/sync/source/subscribe/Subscription.java

## What this is

The handle returned by every subscribe(): isActive reports whether delivery is still live and stop() ends it. It is deliberately tiny because its only job is lifecycle: the screen (or ViewModel) holds it and stops it when observation should end. WrappedSubscription lets sources hand one out before the underlying subscription exists.

## How it fits

Every bind/subscribe call site receives one; ViewModels typically stop theirs in onCleared and fragments in onDestroyView. Leaked subscriptions are the number-one cause of updates hitting dead UI.

## Key pieces

- `isActive/stop` — liveness check plus the single cleanup call every observer must make

## Junior notes

- Store the Subscription where its owner's lifecycle can reach it; a subscription you cannot stop is a leak.
