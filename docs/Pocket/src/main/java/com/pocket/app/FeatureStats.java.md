# Pocket/src/main/java/com/pocket/app/FeatureStats.java
## What this is
A Hilt singleton counting how often headline features are used, plus when the app first launched. Currently tracks LISTEN and READER use counts in a `fcnt` preference group and stamps `firstAppTime` on first run. It is deliberately tiny: if usage-based logic grows, this is the approved place instead of scattering one-shot prefs.
## How it fits
Features call `trackUse(...)` at their usage moment (e.g. opening the reader); gating logic reads `getUseCount(...)`, notably `ReviewPrompt.shouldShow()`, which requires at least 4 reader uses before asking for a Play Store review. `getFirstAppLaunchTime()` serves tenure-based decisions.
## Key pieces
- `Feature` enum (LISTEN, READER with string keys): WHY an enum over raw strings is to keep counter keys centralized and greppable.
- `pref(Feature)`: per-user counters via `counts.forUser(...)`; WHY per-user is that counts must not leak across logouts (forUser prefs are wiped at logout).
- `trackUse` / `getUseCount`: read-increment-write counter; simple and race-tolerant enough for UI-event frequencies.
- `firstAppLaunchTime`: app-level (not per-user) install timestamp, set once when unset.
## Junior notes
- `forUser` vs `forApp` matters here: counters reset on logout by design, while first-launch time intentionally survives it; do not "unify" them.
- The class doc explicitly invites migrating old one-shot prefs onto this component and tracking new features proactively; follow that rather than inventing another counter.
