# sync-android/src/main/java/com/pocket/sync/space/persist/SqliteBinaryStorage.java

## What this is

The Android DumbStorage: persists a whole Space as compact binary blobs (produced by Thing.compress via ByteWriter) in SQLite rows. It handles database lifecycle (onConfigure/onCreate/onUpgrade), snapshot write-back (store), startup load (restore with parsed/restored callbacks), wipe (clear), teardown (release), and identity migration (migrateIdkeys with its IdkeyMigrator and Helper/Transaction/Statements internals) for when server id schemes change. Binary blobs keep the snapshot small and restore fast.

## How it fits

Plugged into MutableSpace as its DumbStorage on Android, usually wrapped in MigrationStorage across upgrades; AppSource then gets restart-surviving state for free. StorageTest-style round trips plus real upgrade tests guard it.

## Key pieces

- `store/restore/clear/release` — the persisted-snapshot lifecycle: write back, startup load, wipe, teardown
- `onConfigure/onCreate/onUpgrade` — SQLiteOpenHelper lifecycle creating and evolving the blob tables
- `migrateIdkeys/IdkeyMigrator` — rewriting stored identities when server id schemes change across versions
- `Transaction/Statements/Helper` — batched-write and statement-cache internals keeping snapshot writes fast

## Junior notes

- onUpgrade paths run on real user devices with real data: a failed upgrade blocks startup, so migrations must be idempotent and tested against old blobs.
- Blobs are opaque by design; never query into them with SQL, read them only through ByteReader restore.
