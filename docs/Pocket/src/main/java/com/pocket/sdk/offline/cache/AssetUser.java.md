# Pocket/src/main/java/com/pocket/sdk/offline/cache/AssetUser.java

## What this is
An ownership tag saying WHO needs a cached file kept on disk: a saved item, one asset referencing another (CSS pulling an image), the app session, or permanent app resources. Files are reference-counted by their users: while at least one `AssetUser` claims a file it is kept; when the last one releases it, the cleaner may delete it.

## How it fits
Downloaders register a user per file (`Assets.registerAssetUser(asset, user)`) — e.g. `TextDownloader` uses `forItem(time_added, idkey)` and `AssetHandler` uses `forParentAsset(...)` for sub-assets. When an item is archived/deleted, its user is unregistered and shared files (one logo used by ten pages) survive until no item needs them. `AssetsDatabase` persists the user rows; `CacheCleaner` trims lowest-priority users first under a cache cap.

## Key pieces
- `type` / `user` / `priority` — owner kind, owner key (item id or parent-asset path), and eviction priority. WHY: the cleaner needs to know what can be deleted and in what order.
- `forItem(timeAdded, idKey)` / `forThing(thing)` — per-save owners, the common case. WHY: ties a file's lifetime to a list item.
- `forParentAsset(asset)` — owner is another cached file (CSS → image). WHY: shared sub-assets stay alive while any parent page needs them.
- `forApp()` / `forSession()` — permanent resources vs current-session scratch. WHY: app-bundled files must survive cleanups that wipe item files.
- `PRIORITY_LOW` / `PRIORITY_HIGH` — eviction ordering ends. WHY: lets trims take expendable files before protected ones.

## Junior notes
- Forgetting to unregister leaks disk space (file kept forever); unregistering too early deletes images out from under saved pages. Match every register with an unregister on the same owner.
- `type="asset"` with a short path (not full path) is how parent-asset users are stored; do not invent new type strings without checking `AssetsDatabase` queries.
