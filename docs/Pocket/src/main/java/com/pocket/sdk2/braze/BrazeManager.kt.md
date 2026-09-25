# Pocket/src/main/java/com/pocket/sdk2/braze/BrazeManager.kt
## What this is
The setup point for Braze (a third-party push/in-app-message marketing SDK): it registers Braze's activity-lifecycle listener for session tracking, installs this class as the in-app-message listener, and links the Braze user to the Pocket user id on login. It also forces dark theme on Braze in-app messages when the app is in dark mode. It is a Hilt `@Singleton` (one shared instance) implementing `AppLifecycle` (login/logout hooks).
## How it fits
Created by Hilt DI (constructor parameters provided automatically) and `setup()` is called at app start. It reads the current user from `PocketCache` (`isLoggedIn`/`uid`) and calls `Braze.changeUser()` so campaigns target the right user. The blocklist passed to `BrazeActivityLifecycleCallbackListener` suppresses in-app messages on transient screens (`AuthenticationActivity`, `AddActivity`, `ListenDeepLinkActivity`, etc.). Display requests flow back through `beforeInAppMessageDisplayed()` before Braze shows anything.
## Key pieces
- `setup()` — registers lifecycle callbacks + in-app-message listener + app-lifecycle observer, then sets the user id. WHY: one call wires the whole Braze integration.
- `onLoggedIn(isNewUser)` — re-runs `setUserId()` after login. WHY: Braze starts anonymous; it must be re-pointed once the Pocket user is known.
- `setUserId()` — no-ops when logged out, else `Braze.changeUser(uid)`. WHY: keeps Braze identity in sync without crashing for logged-out users.
- `beforeInAppMessageDisplayed()` — enables Braze dark theme when `theme.isDark()` and returns `DISPLAY_NOW`. WHY: in-app messages would otherwise flash light on dark-mode screens.
## Junior notes
- `registerInAppMessageManager` with a blocklist means those activities never trigger in-app messages — if a message "mysteriously" does not show, check this list first.
- `changeUser()` switches Braze's profile; calling it with a null uid would orphan the profile, hence the early return — do not "fix" that by passing a placeholder.
