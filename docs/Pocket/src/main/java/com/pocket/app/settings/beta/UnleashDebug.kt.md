# Pocket/src/main/java/com/pocket/app/settings/beta/UnleashDebug.kt
## What this is
This is the internal feature-flag debugger for Unleash (Pocket's remote feature-toggle system). It lists every current flag assignment (name, enabled, variant, payload) in a RecyclerView under a Compose app bar, with pull-to-refresh re-syncing from the server. Each row lets staff force-enable, force-disable, pin a variant, set a payload, or clear the override — writing override actions that sync back and change app behavior within the session.
## How it fits
A Hilt activity launched from BetaConfigFragment's "Unleash Feature Toggles" row; onCreate immediately finishes unless the build is internal-company-only, so release builds can never show it. It reads flags via pocket.bindLocalAsFlow (a Flow observing the local synced Unleash thing) and writes via pocket.sync override/clear actions. The private UnleashDebug object holds the Row model, Adapter, and ViewHolder; the Compose app bar is separate at the file bottom.
## Key pieces
- `UnleashDebugActivity.onCreate`: guards the build flavor, wires swipe-refresh to appSync.sync, hosts the list (initList) plus a Compose app bar (initCompose with DisposeOnViewTreeLifecycleDestroyed so Compose disposes with the view).
- `initList`: collects the Unleash flow, merges current + overridden assignments into sorted Rows, and submits to a ListAdapter (DiffUtil-backed, so only changed rows rebind).
- `editAssignment(name, edit)` + row callbacks (onClearOverride/onForceDisabled/onForceVariant/onOverridePayload): WHY overrides go through Builder edits — the assignment is immutable, so each action rebuilds it with one field changed and syncs an override action with Timestamp.now().
- Text-input dialogs for variant/payload entry; refresh indicator driven by sync callbacks.
## Junior notes
- Overrides are local-first and sync-backed: clearing an override reverts to the server value on next sync — flag states here are debugging aids, not permanent config.
- The Activity mixes view-binding (ActivityUnleashBinding list) with a ComposeView app bar — the two toolkits coexist, but Compose state must use the view-lifecycle disposal strategy or it leaks across rotations.
