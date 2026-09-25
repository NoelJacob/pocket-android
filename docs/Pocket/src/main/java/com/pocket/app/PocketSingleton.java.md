# Pocket/src/main/java/com/pocket/app/PocketSingleton.java
## What this is
Builds and holds the single `Pocket` sync-engine instance for the whole app (created by Hilt, torn down only by logout in `UserManager`). The constructor assembles app/device identity, remote API sources, wakeful thread pools, and storage migrations, then wraps the engine in a `LoggingPocket` request logger on internal builds. It also owns offline-cache bookkeeping: registering asset keep-priorities and cleaning up files for forgotten items.
## How it fits
`PocketModule.providePocket` returns `getInstance()` to every `@Inject Pocket` consumer (repositories, viewmodels, `UserManager`). `App.onCreate` triggers its construction via injection; `WakefulPools` routes the engine's background work through `AppThreads` pools; `AssetCleaner` keeps the `Assets` offline cache consistent with what the engine still remembers.
## Key pieces
- Constructor assembly: `AppInfo` (consumer key, version, store names) + `deviceIdentity` (spoofed via `Device` on internal builds) + remote source (API/article-view/Snowplow analytics endpoints); `LegacyMigration` storage when upgrading, else `IdkeyMigration` for 7.27+ upgrades.
- `LoggingPocket` wrap + `requestLog` (25-entry ring): QA/DEV/DEBUG/PROFILING verbosity for team builds; `dumpRequestLog` exposes recent calls for bug reports; production gets the bare engine (no overhead).
- `assetUser(timeAdded, idKey)` / `assetUser(thing)`: translate items/things into cache keep-priorities honoring the newest/oldest-first setting; WHY these live here is the engine owns identity while `Assets` owns bytes.
- `AssetCleaner`: subscribes to item offline-state changes to delete markup folders; on cache clean, unregisters asset users the engine forgot and resets offline flags so trimmed items know they are no longer offline.
- `WakefulPools`: adapts `AppThreads` pools to the engine's thread pools, flipping the priority scale; wakeful pools keep the device awake until writes land.
## Junior notes
- `teamLoggingLevelPref` takes effect only on next launch (the engine is built once); flipping it in beta settings then expecting live logging will confuse you.
- `clearMarkupFolder` runs on the sync publisher thread, which is the Android UI thread: the TODO admits this is disk I/O on the UI thread, so keep that method trivial and never add more I/O there.
