# Pocket/src/main/java/com/pocket/app/AppCacheCheckActivity.java
## What this is
A now-empty redirect Activity (an Android screen entry point) that immediately forwards to the default activity and finishes itself. It stays in the manifest as the old default/launcher activity purely so users' existing home-screen shortcuts keep resolving. It renders no UI of its own.
## How it fits
The Android launcher fires this when the user taps a legacy shortcut; `onCreate` calls `startDefaultActivity()` (which routes to `MainActivity` or `AuthenticationActivity` via `UserManager.getDefaultActivity()`) and then `finish()` removes it from the back stack, so the user never sees it.
## Key pieces
- `onCreate`: start-default-then-finish; WHY there is no layout is that any visible frame would flash before the real screen.
- `getAccessType` returning `ANY`: anyone, logged in or out, may land here since routing (not auth) is its only job.
## Junior notes
- DO NOT REMOVE this activity: deleting it breaks installed home-screen shortcuts that point at it.
- It is exported (launchable by the system), so treat any incoming extras as untrusted, though currently it ignores them entirely.
