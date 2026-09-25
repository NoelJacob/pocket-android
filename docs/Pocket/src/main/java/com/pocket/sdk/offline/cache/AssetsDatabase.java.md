# Pocket/src/main/java/com/pocket/sdk/offline/cache/AssetsDatabase.java

## What this is
A SQLite database (via `SQLiteOpenHelper`, Android's built-in DB helper) tracking every cached file: its short path, size in bytes, and the list of `AssetUser`s claiming it. It answers "how big is the cache?", "who still needs this file?", and "which files can be deleted?", so deletions never need a filesystem scan.

## How it fits
Owned exclusively by `Assets`: every `registerAssetUser`, `write`/`written`, and unregister call updates rows here on a background `TaskPool` (a small job queue). `CacheCleaner` queries it to trim by priority/age under a cap, and startup reconciliation compares rows against actual files to heal crashes or SD-card swaps. Path conversion helpers translate between absolute disk paths and stored short paths so the DB survives storage-root moves.

## Key pieces
- Asset/user tables + size aggregates — persistent file ↔ owners ↔ bytes ledger. WHY: the source of truth for cache size and safe deletion.
- `convertFullPathToShortPath()` / short-to-full resolution — stores paths relative to the storage root. WHY: the root moves (SD card, setting change); absolute paths would rot.
- Register/unregister/write bookkeeping — keeps row sizes and user lists in sync with disk. WHY: a missed update either leaks space or deletes a live file.
- Trim/eviction queries (by priority, age) — "delete candidates" for the cleaner. WHY: enforces oldest-first / newest-first user preference efficiently in SQL.
- Startup reconcile (DB vs filesystem diff) — drops rows for vanished files and handles orphans. WHY: crashes and card pulls leave the two out of sync.

## Junior notes
- DB writes run off the main thread via futures/tasks; never call them on the UI thread and never assume a just-written row is immediately visible without awaiting.
- Always store short paths in new queries. Storing an absolute path breaks the moment the user moves storage.
- `FutureTask`/`ExecutionException` around DB access means disk failures surface as checked async errors — handle them, do not let them escape onto the UI thread.
