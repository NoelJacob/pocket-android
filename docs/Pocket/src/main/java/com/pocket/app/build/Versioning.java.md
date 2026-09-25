# Pocket/src/main/java/com/pocket/app/build/Versioning.java
## What this is
Answers "is this a fresh install, an upgrade, and from what version?" by comparing the stored previous version code against the current one at startup, and detects OS upgrades via a device-build fingerprint. Components query it for one-time migrations (`upgraded(7,27,0,0)`), and it offers deferred background upgrade work plus a scorched-earth `resetApp` for the logged-out/reset path.
## How it fits
Hilt builds it at startup (constructor persists the current version immediately, so flags describe this launch). `ItemCap` uses `isFirstRun` for the install-size cap; `SaveExtension` uses `from()` for its 7.25 repair; `PocketSingleton` uses `upgraded(...)` for the Idkey migration. `UpdatedReceiver` (package-replaced broadcast) triggers `onAppUpdateReceiver`, scheduling the `UpgradePrep` worker through `Jobs`.
## Key pieces
- Constructor: `to` = current version code, `from` = stored (0 = first run), `isUpgrade` = stored-and-lower, then stores current; `ORIGINAL_BUILD_VERSION` stamped on first run; OS-build key compared for `osUpgraded`; registers the `UpgradePrep` creator with `Jobs`.
- `upgraded(major, minor, point, build)`: "did we cross this release coming from below?"; WHY the canonical migration check is it handles fresh installs (false) and downgrades correctly, unlike raw version compares.
- `addUpgradePrepTask` + `onAppUpdateReceiver` + `UpgradePrep` Worker: deferred background upgrade tasks run after update via WorkManager (Android's persistent job scheduler); exceptions per task are logged-and-ignored so one bad task cannot block the rest.
- `resetApp(assetDirectory)`: `clearApplicationUserData()` (which should kill the process) with 3 retries, then manual files-dir wipe + crash-report as the data-leak failsafe.
## Junior notes
- The constructor writes prefs as a side effect: never construct this twice or in tests without a fake `AppPrefs`, or version history is clobbered.
- Upgrade checks belong in each component's init via `upgraded(...)`, not in `addUpgradePrepTask`; prep tasks are only for long background work that needs no UI and honors user settings (no surprise network).
