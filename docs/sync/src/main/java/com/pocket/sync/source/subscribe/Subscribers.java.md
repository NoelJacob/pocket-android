# sync/src/main/java/com/pocket/sync/source/subscribe/Subscribers.java

## What this is

The source-side helper that implements fan-out: sources keep one instance, register interest with add(Changes, Subscriber), and call publish whenever the Space changes so every matching subscriber gets its update. isActive/stop/stopAll manage lifetime, including the mass-unsubscribe a source needs on teardown. Writing this by hand per source would invite missed deliveries, so the helper centralizes matching and iteration safety.

## How it fits

AppSource and other Subscribeable sources delegate their subscribe/publish duties to this; screens never touch it directly, they only see the subscribe() API and their own Subscriber callbacks.

## Key pieces

- `add` — registers one subscriber's interest for later matching
- `publish` — routes one Space update to every subscriber whose Changes match
- `isActive/stop/stopAll` — per-subscription and global lifecycle controls preventing delivery to dead observers

## Junior notes

- Publishers must never publish after stopAll: AppSource's stopAllSubscriptions exists to enforce exactly that ordering on teardown.
