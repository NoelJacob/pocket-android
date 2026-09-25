# sync/src/main/java/com/pocket/sync/source/subscribe/WrappedSubscription.java

## What this is

A Subscription proxy for when the real subscription is not ready at subscribe time: sources hand this out immediately and plug the real one in later with setSubscription. isActive/stop work correctly in both states (stop before the real one arrives still prevents all future delivery), which removes a whole class of setup races in async bind paths.

## How it fits

Used inside async bind implementations (see AsyncRemoteBackedSource.bindLocal) where the local read returns now but the live subscription resolves a moment later. Screens treat it like any Subscription.

## Key pieces

- `setSubscription` — plugs in the real subscription once available, preserving any earlier stop() call

## Junior notes

- Stopping early must still stick: verify any custom proxy honors a stop() that lands before setSubscription.
