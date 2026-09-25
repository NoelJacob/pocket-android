# Pocket/src/main/java/com/pocket/sdk/offline/cache/StorageLocationPickerDialog.java

## What this is
The "where should offline files live?" dialog: it lists internal storage and any removable SD cards with free-space details and moves the cache root to the user's pick. It also requests the storage permission (Android's runtime grant to read/write shared storage) when needed before showing options.

## How it fits
Launched from Settings storage options and from `StorageErrorResolver` recovery flows. It reads volumes via `AndroidStorageUtil` / `RemovableAndroidStorage`, reports the choice through analytics (`CxtEvent`/`CxtSection` context fields), and on confirm calls back with `retry` so the interrupted cache operation re-runs against the new root. `AssetDirectory` is rebuilt from the new `AndroidStorageLocation` afterwards.

## Key pieces
- `show(context, retry)` — permission check, volume enumeration, dialog display, move-on-confirm. WHY: one entry covers the whole pick-and-migrate flow.
- `Adapter` / `ViewWrapper` / `Option` — list rows binding each volume's label, free bytes (`BytesUtil`), and radio state. WHY: the user needs capacity info to choose sensibly.
- `trackResult(event, context)` — analytics for shown/confirmed/cancelled. WHY: storage moves are destructive-adjacent, so the team tracks outcomes.
- `Manifest.permission` + `PermissionRequester` gating — WRITE_EXTERNAL_STORAGE flow before listing SD cards. WHY: ungranted permission makes removable volumes unusable.

## Junior notes
- Moving the root invalidates every absolute path: anything caching `File` handles across the move will read the old volume. Re-resolve via `AssetDirectory` after the callback.
- `FormFactor` checks in this file adapt the dialog (phone vs tablet); keep that branching when restyling.
- Analytics enums (`Cxt*`) are required fields on the event — a new dismissal path must still log, or dashboards undercount.
