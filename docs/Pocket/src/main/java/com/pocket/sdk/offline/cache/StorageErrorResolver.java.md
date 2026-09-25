# Pocket/src/main/java/com/pocket/sdk/offline/cache/StorageErrorResolver.java

## What this is
The triage UI for offline-storage failures: it inspects the failure (missing permission, missing cache folder, unavailable SD card, critically low free space) and shows the matching dialog — retry, pick storage, free space, or reset the cache. Duplicate simultaneous requests collapse into one dialog and one callback round.

## How it fits
Invoked via `Assets.checkForStorageIssues(activity, callback)` whenever an `AssetDirectoryUnavailableException` (or write failure) escapes file work. It runs checks on `AppThreads` background pools (thread pools for disk/network work), then presents `AlertDialog`s from the given `AbsPocketActivity` (a base activity class with Pocket helpers). Outcomes feed back through `Callback` so the interrupted download can retry or stand down.

## Key pieces
- `resolve(assets, callback, activity)` — synchronized single entry: gathers `Problem`s, shows at most one dialog, fans results out. WHY: several downloads can fail at once; the user must see one prompt, not five.
- `Problem` enum — the diagnosis set (permission, cache-missing, storage-unavailable, low-space). WHY: each maps to a different fix dialog.
- `showRetryOrResetCacheDialog()` / `showFreeSpaceDialog()` — the actual prompts with retry vs wipe-cache actions. WHY: some failures need user action (grant permission), others need destructive recovery.
- `Callback` interface — reports `(issueFound, retry)` back to the caller. WHY: lets the downloader distinguish "fixed, go again" from "user gave up".
- `LOW_SPACE_THRESHOLD` (2 MB) — minimum free space floor. WHY: downloads must fail fast with guidance instead of writing truncated files.

## Junior notes
- Pass a non-null foreground activity or no dialog appears (it degrades to callback-only). Background sync failures therefore only log until the user returns.
- `pendingCallbacks`/`pendingCheck` dedupe is intentional: do not "fix" it by showing one dialog per caller.
- Resetting the cache deletes offline files; confirm the exact scope in the dialog strings before adding new reset paths.
