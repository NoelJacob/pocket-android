# Pocket/src/main/java/com/pocket/app/AppLifecycle.kt
## What this is
The central interface for app-wide lifecycle events: device boot, user present/gone, activity resumed/paused/result, configuration change, low memory, login (`onLoggingIn`/`onLoggedIn`), and logout start. Every method has a default empty body, so components implement only what they need. Its nested `LogoutPolicy` is a strict four-phase contract (stop work, delete data, restart, logged-out) that makes logout data-safe.
## How it fits
Components (UserManager, AppThreads, AppScope, repositories) implement this interface and self-register with `AppLifecycleEventDispatcher`, usually in their `@Inject` constructor. `App` dispatches activity/config/memory/user-presence events; `UserManager` dispatches login events and drives the whole `LogoutPolicy` sequence during logout.
## Key pieces
- `onDeviceBoot`: runs inside a broadcast receiver at boot; WHY it is documented "extremely quick" is that receivers are killed if they block.
- `onUserPresent` / `onUserGone`: foreground-session edges; e.g. `AppOpen` clears stored deep links in `onUserGone`.
- `onLoggingIn(isNewUser)`: off-UI-thread hook after auth succeeds but before the login spinner clears; exceptions are swallowed, so only best-effort work belongs here.
- `onLoggedIn(isNewUser)`: UI is moving to the logged-in experience; safe point to refresh UI-bound state.
- `onLogoutStarted(): LogoutPolicy?`: return null when you hold no user data; otherwise return the four-phase policy.
- `LogoutPolicy.stopModifyingUserData` / `deleteUserData` / `restart` / `onLoggedOut`: WHY four phases instead of one "clear()" is ordering: all components stop first (in parallel), then all delete (no cross-component calls), then all restart, and only in `onLoggedOut` is it safe to use other components again.
## Junior notes
- During stop/delete/restart, do not call other components: they may already be stopped or not yet restarted. `onLoggedOut` is the first safe callback.
- Per-user preferences created with `forUser()` are wiped automatically at logout, so many components need no policy at all; only in-memory caches, pools, and scopes do.
