# sync-parser/src/commonMain/kotlin/com/pocket/sync/usage/UsageMode.kt

## What this is

Decides what codegen may skip: UsageMode labels each definition NORMAL (actively used), COMPAT (deprecated but previously used, kept for restore compatibility), or SKIP (unused, safe to exclude), and UsageModeCalculator computes the labels from the schema plus the UsageFile history (mode() per definition and aspect, removeSkips/getActiveFields filtering, commitUsageFile/id delegating to the ledger). UsageMap indexes who-uses-whom so deprecation cascades correctly. The frank kdoc admits sync-gen currently only honors SKIP, with COMPAT's lighter-touch vision unimplemented.

## How it fits

sync-gen consults this before emitting every class and field: SKIP means no code, which shrinks the app and the binary format. Schema deprecations flow through here into smaller, still-compatible builds.

## Key pieces

- `NORMAL/COMPAT/SKIP` — the three generation fates for definitions and aspects
- `mode() overloads` — per-definition and per-aspect fate computation
- `removeSkips/getActiveFields` — filtered views codegen iterates instead of raw definition lists
- `UsageMap/isUsedActively` — the who-uses-whom index behind deprecation decisions

## Junior notes

- Removing a field from the schema does not remove it from history: the ledger keeps it COMPAT so old disk data still restores.
- Do not rely on COMPAT generating lighter code today: only SKIP has an implemented effect in sync-gen.
