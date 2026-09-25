# Pocket/src/main/java/com/pocket/util/android/NavigationExtensions.kt

## What this is
A one-function guard, `navigateSafely`, that only navigates when the current destination actually has that navigation action. It solves the infamous `IllegalArgumentException: navigation action cannot be found` crash, which happens on rapid double-taps or when a queued navigation fires after the user already moved on. For example, `MainActivity` routes all its post-login navigation events through `navController?.navigateSafely(it)` instead of bare `navigate`.

## How it fits
It is a Kotlin extension on Jetpack `NavController` (the object that manages screen-to-screen navigation from the navigation graph). Callers include `MainActivity` (home/topic/saves/settings routing), `HomeFragment`, `SlateDetailsFragment`, `TopicDetailsFragment` (go-to-Reader flows), and `MyListFragment`. If the action is stale, the call is silently skipped and the app stays put.

## Key pieces
- `navigateSafely(directions)`: looks up `directions.actionId` on `currentDestination` and navigates only when the action exists. WHY it exists: makes every navigation call site race-safe with zero ceremony.

## Junior notes
- `NavDirections` is the type-safe wrapper the navigation graph generates for "go to X with these arguments"; `actionId` identifies the specific transition.
- Skipping is silent by design: a dropped navigation means "the user already left," not an error — do not add fallback navigations around it.
- This guards stale/missing actions only; it does not debounce double-taps that both arrive while the destination is still valid, so buttons that launch slow destinations may still want their own throttling.
