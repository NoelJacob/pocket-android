# Pocket/src/main/java/com/pocket/sdk/dev/TeamTools.java

## What this is
A tiny internal-only dialog for testers and reviewers, showing the current version name and git commit hash with shortcuts to configure or share that info. It does nothing on production builds. It exists so anyone holding a test build can instantly report exactly which build they are on.

## How it fits
Constructed with the current `AbsPocketActivity` (the app's base activity class) and shown from internal UI entry points. `show()` first checks `app().mode().isForInternalCompanyOnly` and returns silently for public users. The "Configure" button opens the internal `TCActivity` settings screen; the share button fires a plain-text send intent (Android's share sheet) with the version string.

## Key pieces
- `TeamTools(AbsPocketActivity activity)` — captures the host activity for app state and navigation. Exists because version info and screen launches both hang off the activity.
- `show()` — builds and shows the `AlertDialog` (a standard Android popup) with version + `BuildConfig.GIT_SHA`, guarded to internal builds. Exists as the whole feature: display, configure, share.

## Junior notes
- The internal-only guard is load-bearing: never remove it or test tooling leaks into the public app.
- `BuildConfig.GIT_SHA` is baked in at build time; if it looks stale, rebuild rather than doubting the dialog.
