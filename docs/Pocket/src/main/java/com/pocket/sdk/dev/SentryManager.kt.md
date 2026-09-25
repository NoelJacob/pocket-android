# Pocket/src/main/java/com/pocket/sdk/dev/SentryManager.kt

## What this is
Initializes the Sentry crash-reporter at app startup and keeps its user identity in sync with the login state. It configures which backend events go to, which build they came from, and what extra context (view hierarchy, user-interaction trails) to attach, while deliberately leaving crash screenshots off for privacy. Each logged-in user is tagged by id so crashes can be correlated without storing personal data.

## How it fits
Singleton created by Hilt DI at startup with the app context, the login-state repository, and the app coroutine scope (a background-task lifetime). Its `init` block calls `SentryAndroid.init` once with the DSN and flavor from `BuildConfig`, then launches a collector on `UserRepository.getLoginInfoAsFlow()` (an observable state stream of login info) that calls `Sentry.setUser` on every login change. Crash reports then flow to the Sentry backend keyed by that user id.

## Key pieces
- `init` / `SentryAndroid.init(...)` — one-time setup: DSN, environment/flavor, view-hierarchy attachment on, screenshots off, user-interaction tracing on, plus a `beforeSend` log hook. Exists so every later crash automatically carries the right build and interaction context.
- Login collector (`getLoginInfoAsFlow().collect { ... Sentry.setUser ... }`) — updates Sentry's user id whenever login state changes. Exists so crashes are attributable per user and stale ids don't leak across logout/login.
- `beforeSend` hook — logs each outgoing event's exception type. Exists as a lightweight visibility aid during development.

## Junior notes
- `Sentry.setUser` with only an id is intentional; do not add emails or other personal data here.
- This runs in `init`, so it executes during injection: keep it crash-safe, since a throw here would break app startup itself.
