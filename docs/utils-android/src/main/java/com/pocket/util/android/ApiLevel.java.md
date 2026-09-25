# utils-android/src/main/java/com/pocket/util/android/ApiLevel.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `ApiLevel` (class, line 13) — Allows for easy and readable Android OS Api Level checks. Also, since the Build.VERSION_CODES aren't available
- `isNougatOrGreater` (fun, line 23) — entry point other code calls; see callers for context.
- `isPreOreo` (fun, line 32) — entry point other code calls; see callers for context.
- `isOreoOrGreater` (fun, line 37) — entry point other code calls; see callers for context.
- `isPreP` (fun, line 45) — entry point other code calls; see callers for context.
- `isPOrGreater` (fun, line 50) — entry point other code calls; see callers for context.
- `hasSystemDarkTheme` (fun, line 56) — entry point other code calls; see callers for context.
- `isLightNavigationBarAvailable` (fun, line 66) — Light Navigation bar is technically available since Oreo MR1 (API 27),

Concrete endpoints referenced here:

- `http://developer.android.com/guide/appendix/api-levels.html`

## Junior notes

- Uses AndroidX platform APIs.
