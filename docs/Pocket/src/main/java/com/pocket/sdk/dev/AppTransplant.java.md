# Pocket/src/main/java/com/pocket/sdk/dev/AppTransplant.java

## What this is
A manual debugging tool that copies a tester's entire app state so a developer can replay it locally. `create()` copies the app's private files directory out to shared external storage, where it can be pulled off the device; the reverse trip (push the folder onto the dev device and launch with a `restore()` hook) boots the app as if logged in as that user. It is deliberately rough, internal-only tooling.

## How it fits
Invoked from the alpha/internal settings screen ("App Transplant"); it is not part of any sync or production flow. It uses Apache `commons-io` `FileUtils` to copy directories and `App`/`Context` file paths to locate the data. The documented workflow isadb pull/push of the `transplant` folder plus a temporary `new AppTransplant(this).restore()` line in `App.onCreate` under the debugger. Note: the checked-in source only contains `create()`/`clear()`; the `restore()` step in the class comment refers to a re-add when needed.

## Key pieces
- `create()` — copies the whole app-data directory (`getFilesDir().getParentFile()`) into `<external-files>/transplant`. Exists to snapshot everything (login, database, prefs) in one folder.
- `clear()` — deletes the exported `transplant` folder. Exists to clean up sensitive data from shared storage after the transfer.
- `outputDir()` / `appDir()` — resolve the export destination and the app-data source. Exist to centralize the two paths the copy/delete operate on.

## Junior notes
- This copies real user credentials and private data; only use with consenting team testers and delete the folder afterwards.
- `getExternalFilesDir` is world-readable-adjacent shared storage while `getFilesDir` is private; that visibility gap is exactly what makes the transfer possible, and why `clear()` matters.
