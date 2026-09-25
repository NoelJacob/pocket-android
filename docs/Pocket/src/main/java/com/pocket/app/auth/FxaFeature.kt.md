# Pocket/src/main/java/com/pocket/app/auth/FxaFeature.kt
## What this is
A one-shot flag for the historic migration from Pocket logins to Firefox accounts: when the login redirect carries `fxa_migration=1`, the app shows a "your account was migrated" snackbar on the next screen. The flag is consumed (reset) on show, so it fires at most once per process. The header comment itself asks when this can be deleted (migration was Q1 2022).
## How it fits
`AuthenticationViewModel.onCredentialsReceived` sets `shouldShowMigrationMessage` from the redirect query param; whatever activity lands after login calls `showMigrationMessage(activity)`, which shows the `PktSnackbar` and clears the flag.
## Key pieces
- `shouldShowMigrationMessage`: plain in-memory boolean; WHY not persisted is the notice only makes sense in the login-then-land window of the same process.
- `showMigrationMessage(activity)`: guarded show-and-clear; null-safe on the activity.
## Junior notes
- Because the flag is memory-only, a process kill between login and next screen silently drops the notice; that is accepted behavior for a one-time nicety, not a bug to "fix" with prefs.
- If product confirms the migration cohort is gone, delete this class plus its two call sites rather than leaving a permanently-false flag.
