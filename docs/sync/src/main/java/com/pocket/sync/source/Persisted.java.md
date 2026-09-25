# sync/src/main/java/com/pocket/sync/source/Persisted.java

## What this is

The blocking contract for controlling what a Source keeps in its Space: which Things are remembered, forgotten, stored to disk, and restored on launch. Persistence here means cache lifetime (what survives process death), not server sync. AsyncPersisted is the callback twin for slow stores.

## How it fits

Persisted Spaces (MutableSpace over SqliteBinaryStorage on Android) implement this so the app can trim caches and survive restarts. Holders passed to remember/forget decide per-owner lifetime.

## Key pieces

- `remember/forget/store/restore controls` — the cache-lifetime operations that decide what the Space holds onto

## Junior notes

- Remembering without ever forgetting is a memory leak with extra steps; pair every remember with a matching forget tied to the owner's lifecycle.
