# Pocket/src/main/java/com/pocket/sdk/api/AppSync.java

## What this is
The app-wide scheduler that keeps the user's library in sync with the Pocket server. It owns the "sync now" entry point, merges extra data requests from other components into one server call, and runs registered follow-up work after each sync. Think of a sync as: push queued local changes up, pull what changed on the server down, update the local store.

## How it fits
Created once by Hilt DI (constructor parameters provided automatically) as a singleton; UI and background triggers (app open, login, manual refresh, background sync) call `sync()`. It reads/writes the local store through `Pocket` (`remember`/`initialize`/`sync`/`syncRemote`) and talks to the server with the generated `Get`/`Fetch` request models. Components like `SendToFriend`, `UserMessaging`, and `ServerFeatureFlags` plug in via `addFlags`/`addWork` instead of running their own syncs. A save traced end to end looks like: UI calls `pocket.sync(saveAction)` to queue the change locally, then `AppSync.sync()` pushes pending actions and pulls a `Get` delta so the list UI reflects both the local save and any server-side changes.

## Key pieces
- `sync()` / `sync(onSuccess, onFail, progress)` — the single entry point; if a sync is already running it attaches the new listeners instead of starting a second one. This coalescing is why many callers can safely request syncs.
- `SyncTask` — the background worker that runs one sync: logged out it only pushes actions; first login it runs the multi-chunk `Fetch` bootstrap; afterwards it does the normal `Get` delta plus registered `SyncWork`. Exists to centralize the three sync modes in one place.
- `addWork(SyncWork)` / `addWork(Runnable)` — lets a feature run code during every app sync and receive the fresh `Get` response. Exists so features piggyback on one network round trip.
- `addFlags(GetFlags)` / `addInitialFlags(GetFlags)` — lets a feature ask for extra sections (tags, premium, shares) in the shared `Get` request. Exists so one request serves many features.
- `getFlags(...)` — merges every contributor's flags into a single master `Get` builder, taking the max/OR of each flag. Exists as a guardrail: contributors can only turn flags on, never rewrite identity fields.
- `hasFetched()` / `addFetchedWork(Runnable)` — tracks whether the initial bootstrap completed and fires hooks when it does. Exists so UI can wait for real data before rendering.
- `Sender` (inner class) — pushes urgent queued actions immediately, on app background, and when connectivity returns. Exists because non-urgent actions otherwise wait lazily for the next sync.

## Junior notes
- Local-vs-remote: `pocket.sync(thing)` resolves against the local store first (fast, offline-safe); `syncRemote` forces a network round trip. `AppSync` deliberately mixes both.
- `SyncWork.sync()` returning a `PendingResult` means "async work still running"; returning null means "done". Exceptions inside work are swallowed so one feature can't break the whole sync.
- Never call back into `AppSync` from inside `SyncWork.sync()`; the docs warn this can deadlock.
