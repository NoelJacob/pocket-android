# Pocket/src/main/java/com/pocket/sdk/api/ServerFeatureFlags.kt

## What this is
Reads server-driven feature flags and A/B-test assignments (Unleash "toggles") and exposes them to the app. It caches the current assignments locally, refreshes them from the server during sync and login, and fires an analytics enrollment event when a caller actually uses an assignment. Think of it as: the server decides which variant this device/user gets; this class fetches, stores, and hands out that decision.

## How it fits
Singleton created by Hilt DI; on startup it registers an `unleash` thing with `Pocket` (persisted under a `"unleash"` holder) and adds a refresh step to `AppSync` via `addWork`, so every app sync also refreshes flag assignments. Feature code calls `get(flag)`; the class reads the cached `current_assignments` map from the local store and enrolls the user for tracking. `refresh()` does the network part: it syncs the login info and device guid, then requests assignments with that user/session context. Tracing a save-like flow: UI asks `get("my-flag")`, gets a variant from local cache instantly, and the next `AppSync` run re-pulls assignments from the server in the background.

## Key pieces
- `get(flag, view)` — returns the assignment and enrolls the user in the test (the default every caller should use). Enrollment matters because analytics only counts users enrolled at the point they see the variant.
- `getWithoutEnrolling(flag)` — reads the assignment without enrolling; for invisible work like preloading where the user sees no difference yet. Exists for the narrow case approved with the data team; callers must `get` later when the UI actually shows.
- `refresh()` — three-step network fetch (login info, then device guid, then assignments with that context) run on the app scope. Exists so assignments stay keyed to the right user/session after login/logout.
- `onLoggingIn(isNewUser)` — blocks briefly on `refresh()` at login. Exists as a best effort so post-login UI sees the new user's flags, not the previous user's.
- `parsePayload(payloadCreator)` (top-level helper) — parses a flag's JSON payload into a typed model from `local.graphqls`. Exists so flags can carry configuration, not just on/off.
- `baseContext` — the device/app context (app name, prod-vs-alpha environment, locale) sent with every assignment request. Exists because targeting rules depend on it.

## Junior notes
- All reads are async (`PendingResult`) and served from the local cache; there is no synchronous getter, so cache the value in your component if you need it fast, and clear it on logout.
- Internal builds can override assignments; production always uses the server value. Checking a flag fires a tracking event only when an assignment actually exists.
