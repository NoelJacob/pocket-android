# Pocket/src/main/java/com/pocket/sdk2/api/legacy/IdkeyMigration.java
## What this is
A one-shot data migration that rewrites persisted item identifiers ("idkeys") from an old buggy algorithm to the fixed one. Between versions 7.25 and 7.27 the idkey computation changed, so every stored idkey — in the sync database, the offline asset database, and on-disk markup folder names — had to be translated. It runs once on upgrade from a pre-7.27 install and is a no-op otherwise.
## How it fits
A `sync` here means reconciling the local database ("local": what is on the phone) with the server ("remote": the user's canonical Pocket list). Local rows are keyed by idkey, so a key-format change breaks the link between cached things, downloaded article files, and markup folders. Invoked as a `MigrationStorage.Access` during `Pocket` startup (see `PocketSingleton`); it calls `SqliteBinaryStorage.migrateIdkeys(spec)` to build an old→new map, renames markup directories under the asset directory, calls `assets.fixIdKeys()`, then commits the storage migration.
## Key pieces
- `IdkeyMigration(Assets, ErrorHandler)` — constructor wiring: needs the offline asset store (to fix folders/db) and the error reporter. WHY: the migration touches two storage systems at once.
- `transform(Spec, DumbStorage)` — the whole migration: build the key map, rename markup dirs, fix the asset db, commit. WHY: single ordered unit so a crash before `commit()` leaves old keys intact for retry.
- `reportedError` flag — reports the failure to the error tracker only once even if startup retries the migration. WHY: avoids spamming crash reports on every launch while still failing loudly.
## Junior notes
- Sync-protocol concept used here: an idkey is the stable local identity of a synced object — if it changes, the local cache can no longer match its rows to server updates, hence a migration instead of a resync.
- Never delete this file casually: the header comment says it can go only when upgrades from pre-7.27 are no longer supported.
