# Pocket/src/main/java/com/pocket/app/notes/Notes.kt

## What this is
A tiny gatekeeper that decides whether the Notes feature is visible at all. Notes are internal-only and still behind server flags, so this is the single on/off switch every Notes entry point consults.

## How it fits
A `@Singleton` injected into the Saves tab / navigation code. Callers `await` (suspend until the value arrives) `areEnabled()` before showing Notes UI or destinations; the list-embedded Notes panel and the note-details screen both stay hidden unless this returns true.

## Key pieces
- `areEnabled()` — WHY: ANDs three conditions — internal-company build (`AppMode.isForInternalCompanyOnly`), the dev flag assigned, and the kill switch *not* assigned.
- `KillSwitch` / `DevelopmentFlag` — WHY: server-side flag keys; the kill switch lets the team disable Notes instantly without a release.

## Junior notes
- All three checks are server-flag reads (suspending network/cache lookups) — never call this on the main thread without a coroutine; cache the result per screen rather than polling.
- Returning false for all public builds is intentional while Notes is in development; do not "fix" it as a bug.
