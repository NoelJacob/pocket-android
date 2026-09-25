# Pocket/src/main/java/com/pocket/sdk2/api/legacy/LegacyMigration.java
## What this is
The big one-time upgrade from the pre-7.42 storage world (old `DbDelegate`/`ListOperation` sqlite database plus scattered preferences) to the current sync engine (`Pocket` + its `Space`, the local synced-object database). It moves items, friends, login state, assets, and markup folders into the new format when a user upgrades from 6.7.0.1 or later, and resets the app for older installs. It is retry-safe: failures leave old data in place for the next launch.
## How it fits
A `sync` here means the engine comparing local state (the `Space` database on the phone) with remote state (the server) and exchanging changes; a `Space` is the local store of synced objects ("things": items, tags, account data). `PocketModule.createIfNeeded()` decides whether the migration runs and hands the instance to `PocketCache` (which seeds its cached login from `loginInfo()`) and to `Pocket` (which uses `storage()` as its migration source). The heavy lifting happens inside `DumbStorage.restore(...)` (a bulk-load callback: the engine asks the old store to hand over every saved object), which the app already expects to take a few seconds — to the user it looks like a slightly longer first list load.
## Key pieces
- `createIfNeeded(...)` — decides run vs. skip (version checks, logged-in state, fetch completion) and kicks off quiet background prep via `Versioning.addUpgradePrepTask()`. WHY: so the work is done before the user opens the app when possible.
- `storage()` — exposes the old data as a `MigrationStorage.Source` for `Pocket` to import. WHY: lets the new engine pull from the old store through its normal restore path.
- `loginInfo()` — carries the migrated login/session into `PocketCache`. WHY: the user must stay logged in across the upgrade.
- Markup/asset moving (unique_id → idkey folder renames, old asset sqlite → `AssetsDatabase`/`AssetUser`s) — repoints downloaded article files at the new keys. WHY: without it, saved-for-offline articles would be orphaned.
- Pre-6.7 reset path — wipes to defaults instead of migrating. WHY: the v3 API shutoff made those upgrades untestable and they cover <0.2% of upgraders.
## Junior notes
- Repository caching angle: after this runs once, `PocketCache` and the `Space` database are the caches — this class is the bridge that filled them from the old world; normal launches never touch it.
- To trace a save end to end post-migration: UI action → sync `Action` → `Space` (local) → sync to server (remote). This file only matters for the one launch that populates `Space` the first time.
- Cleanup note from the header: when the 6.7 upgrade path is dropped, delete this class plus its references in `PocketModule`, `PocketCache`, and `Pocket` — it was designed for that.
