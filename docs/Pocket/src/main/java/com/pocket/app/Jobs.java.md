# Pocket/src/main/java/com/pocket/app/Jobs.java
## What this is
A thin Hilt-singleton wrapper around Android WorkManager (the OS background-job scheduler that runs deferred work even if the process dies). You register a `WorkCreator` factory for each `Worker` subclass once, then schedule it one-off, periodically, immediately, or cancel it by its unique class name. It centralizes WorkManager init (log level, custom factory) so features never touch the raw API.
## How it fits
Initialized with the app context at startup. `Versioning` registers `UpgradePrep` here and triggers it from the package-replaced receiver; other features register their sync/cleanup workers the same way. Scheduling is by unique work name (the worker class name), so rescheduling replaces rather than duplicates.
## Key pieces
- Constructor: `WorkManager.initialize` with dev-verbose/prod-info logging and `CreatorWorkerFactory`; WHY manual init is that the factory must be installed before any `getInstance()` call.
- `registerCreator`: maps worker class name to its factory; WHY a factory instead of reflection is that workers can take Hilt-provided constructor deps.
- `scheduleOneOff(worker, startInMs, networkType)`: delayed one-shot with a network constraint (e.g. NOT_REQUIRED vs CONNECTED).
- `schedulePeriodic(worker, intervalMs)`: repeating work, always requiring connectivity by default.
- `scheduleImmediate` / `cancel`: run-now and cancel-by-name.
- `CreatorWorkerFactory`: looks up the registered factory, else falls back to the default; unregistered workers still construct if they have a default constructor.
## Junior notes
- Unique-work REPLACE semantics mean scheduling the same worker twice does not queue two copies; the second schedule wins, including its delay and constraints.
- The class itself flags the API footgun: nothing stops you from calling `schedule*` before `registerCreator`, which silently falls back to default construction; always register during app init.
