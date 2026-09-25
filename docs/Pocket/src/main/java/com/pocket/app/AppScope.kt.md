# Pocket/src/main/java/com/pocket/app/AppScope.kt
## What this is
The app-wide coroutine scope (coroutines are Kotlin's lightweight background tasks; a scope is the handle that owns and can cancel them). It lazily builds a `SupervisorJob + Dispatchers.Default` scope, hands it out as this object's `coroutineContext`, and exposes a logout policy that cancels everything at logout and rebuilds fresh on restart. Long-lived background work that is not tied to one screen launches here.
## How it fits
Injected anywhere needing fire-and-forget app-level work. At logout, `UserManager` collects its `LogoutPolicy` and runs it deliberately last among the stop-phase policies, because logout's own async steps may still need a live scope; then `restart()` nulls the scope so the next access lazily creates a clean one for the next user.
## Key pieces
- `scope` lazy getter: double-checked under `lock`; WHY lazy instead of init-time is so `restart()` can drop it and the next user transparently gets a new one.
- `getLogoutPolicy().stopModifyingUserData`: cancels the live scope ("Logout."); WHY cancel rather than just stop accepting work is that in-flight user-data writes must not complete after the account switches.
- `getLogoutPolicy().restart`: sets `_scope = null` without creating a replacement; WHY deferred recreation is that creating threads for a logged-out app with no work yet would be wasteful.
## Junior notes
- A cancelled coroutine scope can never be reused, which is exactly why `restart()` nulls instead of just cancelling; launching into the old reference after logout would fail silently.
- `SupervisorJob` means one failing child coroutine does not cancel its siblings; still handle per-task errors, since uncaught failures vanish into the supervisor.
