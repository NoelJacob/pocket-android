# sync/src/main/java/com/pocket/sync/source/subscribe/PublishingSubscriber.java

## What this is

A Subscriber wrapper that hops threads: it receives onUpdate on whatever thread the Space changed on, then re-publishes through a Publisher (for example AndroidUiThreadPublisher) so downstream UI code always runs on the right thread. setSubscription wires the lifecycle link that guarantees onUpdate is never invoked after stop(), closing a classic race. hasBeenInvoked lets diagnostics check whether delivery happened.

## How it fits

Sources wrap raw screen subscribers in this before registering them, so Space internals never need to know about UI threads. It pairs with WrappedSubscription, which plays the same proxy role on the Subscription side.

## Key pieces

- `Publisher hop` — the thread redirect making Space callbacks safe for main-thread UI code
- `setSubscription` — the lifecycle link preventing post-stop delivery races
- `hasBeenInvoked/onUpdate` — delivery entry point plus a flag for asserting delivery in tests

## Junior notes

- Thread-hopping adds ordering guarantees (one at a time, in order) but also latency: keep onUpdate bodies small.
