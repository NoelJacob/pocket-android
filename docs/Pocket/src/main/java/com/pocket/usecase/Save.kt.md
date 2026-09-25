# Pocket/src/main/java/com/pocket/usecase/Save.kt
## What this is
The single gate for saving a URL: if the user is logged in, it queues the save; if not, it reports `NotLoggedIn` so the UI can prompt sign-in. Junior trace from tap to network: button calls `Save(url)` → `userRepository.isLoggedIn().first()` checks the cached login → logged in: `itemRepository.save(url)` runs `pocket.sync(null, add...)` and `.await()`s the server round trip → success returns `Result.Success`.
## How it fits
Injected (via Hilt DI) into share targets, save buttons, and add-by-URL flows. It sits above `ItemRepository` (the writer) and `UserRepository` (the gate); downstream is the sync engine's `add` action queue and then the server. A "sync" here is that queued action: applied to the local cache immediately, sent to the server in the background.
## Key pieces
- `invoke(url)` (`suspend operator fun invoke`: a pausable background-task function callable as `save(url)`) — checks login first, saves second; WHY this order: anonymous saves would orphan items with no account to sync to.
- `Result` (`Success`, `NotLoggedIn`) — a closed outcome enum; WHY not Boolean/exception: the "not logged in" case is an expected branch needing its own UI, not an error. `@CheckReturnValue` forces callers to handle it.
## Junior notes
- `isLoggedIn().first()` takes the current value of the login stream; if login state flips mid-save, this check is already stale, so re-check on failure.
- Unlike most repository mutations (fire-and-forget), this path `.await()`s inside `ItemRepository.save()`; the UI can show a real saved/failed state instead of assuming success.
