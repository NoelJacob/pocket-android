# Pocket/src/main/java/com/pocket/sdk2/api/legacy/PocketCache.java
## What this is
A deprecated synchronous bridge that keeps an in-memory + encrypted-on-disk copy of the user's login/account state (`LoginInfo`: access token, account, premium flags) so old UI code can read it instantly on the main thread. The modern `Pocket` sync engine loads state asynchronously (a background task returning later via callback), which much of the app was never rewritten for — this class papers over that gap. Values can be briefly stale in race conditions.
## How it fits
A `sync` here is the engine keeping the local `Space` database and the server in agreement; `PocketCache` is a read shortcut, not part of that protocol — it subscribes to `LoginInfo` changes from `Pocket` (`pocket.bind(...)` with a `PublishingSubscriber`, a callback invoked on the calling thread) and caches the latest value. Old callers (`BrazeManager`, premium checks, `UserManager`-era code) read `isLoggedIn`, `getEmail`, `hasPremium` synchronously. On logout it clears the encrypted pref; on upgrade it seeds from `LegacyMigration.loginInfo()`. Constructed by Hilt DI (constructor parameters provided automatically) as a `@Singleton` (one shared instance app-wide).
## Key pieces
- `updateCache(LoginInfo)` — writes the new login to memory and to the encrypted `PKT_CACHE` pref (encrypted with `TinkEncrypter` because it holds access tokens). WHY: the single write path keeping memory and disk in agreement.
- `cached()` — lazy read: memory first, else decrypt-from-disk, else empty `LoginInfo`. WHY: instant synchronous reads without touching the sync engine.
- `isLoggedIn()` / `loginInfo()` / `account()` / `getEmail()` / `getUsername()` / `getUID()` — synchronous accessors over the cached login. WHY: the legacy UI contract this class exists to serve.
- `hasPremium()` / `hasFeature()` / `hasPremiumTrial()` / `isPremiumUpgradeAvailable()` — premium gates (currently forced to unlocked for local builds via `ponytail:` overrides). WHY: feature gating without async calls.
- `onLogoutStarted()` (`AppLifecycle` callback: hooks for login/logout events) — clears the pref and memory on logout. WHY: no session leakage between users.
## Junior notes
- `Subscriber.onUpdate()` ordering is not guaranteed: if your subscriber runs before this cache's, you will read stale data — prefer loading from `Pocket` directly in new code.
- Coroutines are Kotlin's background tasks; this class deliberately avoids them to serve main-thread callers — do not call it from a coroutine expecting fresh data, it is a snapshot.
