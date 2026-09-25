# Pocket/src/main/java/com/pocket/repository/UserRepository.kt
## What this is
Exposes login and Premium state as observable streams, all derived from one cached `LoginInfo` object. It answers "is there a logged-in user?" and "should we show Premium upsells or Premium styling?".
## How it fits
Injected (via Hilt DI: constructor parameters provided automatically) into anything gating on account state: the `Save` use case checks `isLoggedIn()` before saving, settings/paywall screens observe the Premium flows. Everything funnels through `getLoginInfoAsFlow()` (`bindLocalAsFlow` on `loginInfo`), so one local cache update fans out to all observers. `Flow` here means an observable stream that re-emits when login state changes.
## Key pieces
- `UserRepository` (interface) — `isLoggedIn`, `isPremiumUpgradeAvailable`, `hasPremiumDisplaySettings`, `getLoginInfoAsFlow`; `SyncEngineUserRepository` maps each to the shared login flow plus `distinctUntilChanged()` (suppresses repeat emissions so the UI doesn't redraw on identical values).
- `isLoggedIn()` — true when `access_token != null`; the token is the session proof.
- `hasPremium()` / `isFree()` / `isPremiumUpgradeAvailable()` — upgrade prompt shows only when logged in AND free; `hasPremiumDisplaySettings()` currently just mirrors `hasPremium()` as a seam for a future separate flag.
## Junior notes
- `hasPremium()` is hardcoded to `true` (marked `ponytail:` for local builds), so `isPremiumUpgradeAvailable()` is always false here; don't trust paywall gating in dev builds.
- `distinctUntilChanged()` means collectors only fire on actual transitions (login/logout, free/premium), not on every cache touch.
