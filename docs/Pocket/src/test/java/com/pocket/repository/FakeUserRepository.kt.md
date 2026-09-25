# Pocket/src/test/java/com/pocket/repository/FakeUserRepository.kt
## What this is
Trivial fake of `UserRepository` (login/premium state). Boolean flags (`loggedIn`, `premium`, `hasPremiumDisplaySettings`) drive `isLoggedIn()`, `isPremiumUpgradeAvailable()`, and `hasPremiumDisplaySettings()` flows, with a stub empty `LoginInfo`. Lets tests fix the user state in one line.
## How it fits
Used alongside `FakeItemRepository` to build the real `Save` use case in `AddUrlBottomSheetViewModelTest` and similar suites. The fake exists because the real repository needs account/network state; most ViewModel logic only branches on logged-in/premium booleans.
## Key pieces
- `loggedIn / premium / hasPremiumDisplaySettings` flags — public vars; WHY: tests set user state directly without auth flows.
- `isLoggedIn() / isPremiumUpgradeAvailable() / hasPremiumDisplaySettings()` — each emits the flag as a one-shot flow; WHY: matches the repository's reactive (`Flow`, an observable state stream) interface.
- `getLoginInfoAsFlow()` — emits an empty `LoginInfo`; WHY: satisfies the interface for tests that never inspect login details.
## Junior notes
- Flows here emit once and complete; code that collects continuously will see completion, not updates — use a `MutableStateFlow` mock if you need changing values.
- `isPremiumUpgradeAvailable` is the inverse of `premium`: setting `premium = true` hides the upgrade.
