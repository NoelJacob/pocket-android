# Pocket/src/main/java/com/pocket/sdk/notification/push/PktPush.kt
## What this is
The real implementation of push registration: it gets the device's Firebase (FCM) token, sends it to the Pocket server via a `register_push` sync action, and remembers which user GUID it registered for. Deregistration and re-registration after token rotation live here too.
## How it fits
Bound in DI as the `Push` interface; `App.push()` exposes it. `FcmMessageService.onNewToken()` calls `invalidate()` when Firebase rotates the token. `register()` fetches the token from `FirebaseMessaging`, resolves the logged-in GUID through a `Pocket.sync()` of login info, then sends `register_push` with a production push type and an analytics `ActionContext`; `deregister()` sends `deregister_push` for the stored GUID. State is three prefs: `registeredGuidFirebase`, `dev_pref_fcm_token` (internal builds only), and `reregisterFirebase`.
## Key pieces
- `register(cxt_ui, listener)` — async token fetch then background-thread sync; on success stores the GUID/token and clears the reregister flag, on `SyncException` (a sync-layer network failure) reports `onResult(false, message)`.
- `deregister(cxt_ui)` — sends `deregister_push` for the stored GUID and clears it on success; no-ops when push is unavailable.
- `invalidate()` — sets `reregister=true` and re-registers immediately; the constructor also enqueues a re-register on the next `AppSync` work pass if the flag was left set (e.g. app died mid-rotation).
- `isAvailable()` / `getToken()` — available means a user is logged in (`pktCache.isLoggedIn`); the token getter returns the stored FCM token only on internal builds, null in production.
## Junior notes
- Registration needs two async pieces (FCM token plus server GUID), so failures report through `RegistrationListener.onResult` on the UI thread; never assume `register()` has completed when it returns.
- `ActionContext` here is analytics bookkeeping (which UI triggered the registration), not auth; the actual identity sent is the login GUID plus the FCM token.
