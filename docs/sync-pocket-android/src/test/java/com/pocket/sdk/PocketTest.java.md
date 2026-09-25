# sync-pocket-android/src/test/java/com/pocket/sdk/PocketTest.java

## What this is

End-to-end Pocket SDK behaviors against fakes: unleash/gsf flag paths, automatic guid acquisition when syncing Things versus Actions, cache persistence across instances (persists), and the headline save flow. These are the closest thing to product-level proof that the SDK layer turns app intents into synced state.

## How it fits

Runs on the AbsPocketTest fixture with mocked transports; failures here mean SDK wiring (not engine internals) regressed. PocketV3SourceTest covers the transport beneath with finer granularity.

## Key pieces

- `save` — the headline add-URL flow from action through synced state
- `automaticallyObtainsGuidFromSyncingThing / ...Action` — guid bootstrapping on both the query and mutation paths
- `persists` — cache survival across SDK instances
- `unleash / gsf` — feature-flag and service-framework integration paths
- `automaticallyObtainsGuidFromSyncingAction` — further cases in this file covering adjacent behavior of the same contract

## Junior notes

- Guid acquisition tests guard first-run behavior: a regression here breaks every fresh install's first sync.
