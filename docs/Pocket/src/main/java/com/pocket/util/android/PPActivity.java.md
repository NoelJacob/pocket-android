# Pocket/src/main/java/com/pocket/util/android/PPActivity.java

## What this is
A "Process Phoenix" Activity that restarts the whole app process, used for fundamental state changes in internal builds (e.g. flipping between staging and production backends). It solves the problem that some changes only take effect with a fresh process: instead of asking developers to force-stop the app, the app kills and rebirths itself. For example, `BetaConfigFragment` calls `PPActivity.triggerRebirth(getActivity())` after a beta-config change and finishes other activities first. (It is a renamed copy of Jake Wharton's ProcessPhoenix library so it can be declared clearly in the manifest.)

## How it fits
`BetaConfigFragment` is the trigger: it finishes all activities, then calls `triggerRebirth(context)`. `triggerRebirth` builds (or accepts) the restart intent, fires the invisible `PPActivity` with `FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK`, and that Activity starts the next intent and finishes, leaving a clean process. The `IS_ENABLED` flag gates everything on internal (`isForInternalCompanyOnly`) builds via `App`.

## Key pieces
- `triggerRebirth(context)` / `triggerRebirth(context, nextIntent)`: entry points that schedule the restart with the default launcher intent or a supplied one. WHY two forms: most callers want "just restart the app"; the second supports restarting into a specific screen.
- `getRestartIntent(context)`: resolves the default restart target from the package manager. WHY it exists: rebirth should land on the normal entry point, not hardcode one.
- `onCreate(savedInstanceState)`: reads the restart intent extra, launches it, finishes, and kills the old process. WHY a whole Activity: only an Activity launch can reliably clear the task stack before the kill.
- `IS_ENABLED` / `KEY_RESTART_INTENT`: the internal-build gate (no-op on public builds) and the intent-extra key carrying the next intent.

## Junior notes
- After calling `triggerRebirth`, the current process's behavior is undefined — return immediately and touch nothing afterwards.
- This is deliberately inert in production builds (`IS_ENABLED` false makes it return silently), so never rely on it for user-facing flows.
- The class must stay declared in `AndroidManifest.xml`; without the manifest entry the restart intent resolves to nothing.
