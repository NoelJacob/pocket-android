# Pocket/src/main/java/com/pocket/util/android/IntentUtils2.kt

## What this is
A one-function Kotlin companion to `IntentUtils` that answers "can anything on this device handle this intent?" using the modern package-manager query. It solves the need for a null-safe, Kotlin-friendly availability check without dragging the whole legacy Java helper along. For example, `IntentUtils.hasGoogleTranslate(context)` delegates to `isIntentUsable` with the Translate intent before offering translation.

## How it fits
It is a Kotlin `object` (singleton) in the Android util package. Its known caller is `IntentUtils.hasGoogleTranslate`, which pairs it with `getGoogleTranslateIntent()` for a check-then-fire flow. It queries `PackageManager.queryIntentActivities` with `MATCH_DEFAULT_ONLY` and returns whether the match list is non-empty.

## Key pieces
- `isIntentUsable(context, intent)`: the only function. Queries handlers in the DEFAULT category and reports non-emptiness. WHY it exists: a minimal modern check that new Kotlin code can call without the legacy Java surface.

## Junior notes
- `MATCH_DEFAULT_ONLY` restricts matches to activities that declare `CATEGORY_DEFAULT` — the ones that genuinely volunteer for implicit intents. Dropping that flag would over-report handlers.
- An implicit intent names an action (e.g. "translate this"), not a target class; availability can change with installs, so check at use time, not at startup.
- If you need guarded launching, toasts, or browser picking rather than a bare Boolean, use `IntentUtils` instead — this file answers only "is it usable?".
