# Pocket/src/main/java/com/pocket/sdk/dev/ErrorHandler.kt

## What this is
The app's minimal error-reporting outlet: one method logs a handled exception, another escalates it. In internal builds, unexpected errors crash immediately so developers notice; in production the same call just prints the stack trace. It exists as a single choke point so the crash-vs-log policy can change later without touching every caller.

## How it fits
Singleton injected anywhere that catches an exception (`AppVersion`, `ServerFeatureFlags`, and others call `reportError`). It reads `AppMode` (the build-flavor switch provided by Hilt DI, where constructor parameters are supplied automatically) to decide behavior. Downstream today is just `printStackTrace`; a real crash-reporter integration would attach here.

## Key pieces
- `reportError(throwable)` — records a caught/handled exception (currently stack-trace logging). Exists as the standard "something went wrong but we recovered" call.
- `reportOnProductionOrThrow(throwable)` — throws in internal builds, reports in production. Exists for "this should never happen" checks: fail fast for the team, stay alive for users.

## Junior notes
- `isForInternalCompanyOnly` is true for team/alpha builds, not just local dev builds; both will crash on `reportOnProductionOrThrow`.
- The `@Suppress("TooGenericExceptionThrown")` annotation quiets the linter about throwing a generic `Throwable`; it is intentional here.
