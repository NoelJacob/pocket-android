# Pocket/src/main/java/com/pocket/app/auth/AuthenticationActivity.java
## What this is
The login wall's host Activity: the first screen fresh installs and logged-out users see. It creates (or reattaches) the `AuthenticationFragment`, forwards auth-redirect intents to it, and gets out of the way once login completes (finishing itself on restart when already logged in). It also carries the historic installer-launch stack fix.
## How it fits
Launched via `startActivity(context, skipOnboarding)` from `HomeFragment` (signed-out saves), `UserManager` logout restart, and app-start routing. It hosts the fragment under tag `FRAGMENT_MAIN`; `onNewIntent` forwards `pocket://auth` redirects by scanning attached fragments (tag lookup proved unreliable on some devices). On success the fragment calls `startDefaultActivity()`, which resolves to `MainActivity` through `UserManager.getDefaultActivity()`.
## Key pieces
- `startActivity(context, skipOnboarding)`: the typed entry; `skipOnboarding=true` jumps straight to browser auth (used when the user explicitly tapped sign-in elsewhere).
- `onCreate` + `onRestoreInstanceState`: creates the fragment only on first creation, reattaches by tag after rotation; WHY the null-`mFrag` dance is the fragment manager owns the instance across config changes, not the activity field.
- `onNewIntent`: forwards to any attached `AuthenticationFragment`; WHY a loop over fragments is a device-specific bug where tag lookup returned null.
- `launchFix` + `checkForFailedLaunchFix`: works around the installer-vs-launcher double-task bug (`FLAG_ACTIVITY_BROUGHT_TO_FRONT` finish) with a reporting failsafe that disables itself (`ALLOW_LAUNCH_FIX`) via `FailedLaunchFixException` if it ever strands the user.
- `getAccessType() = LOGIN_ACTIVITY`: marks this as the login gate in the access-control scheme; `supportsRotationLock()=false` and clipboard-check disabled keep the login screen simple and non-intrusive.
## Junior notes
- `onRestart` finishes when logged in: pressing back into this after login must never show the login wall again; do not remove that guard or logged-in users can navigate back to sign-in.
- `checkClipboardForUrl` is intentionally empty here: every other screen offers "open copied link?", but doing that on the login wall would hijack the first-run experience.
