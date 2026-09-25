# Pocket/src/main/java/com/pocket/app/settings/cache/CacheSettingsActivity.java
## What this is
This is the thin full-screen host for the offline-cache settings on phones. It offers static startActivity/newStartIntent helpers and places a CacheSettingsFragment as its content on first creation. All behavior (temp prefs, save/discard, cleaner runs) lives in the fragment.
## How it fits
Launched from PrefsFragment's "Manage Cache Limits" row via CacheSettingsFragment.show() — which picks this activity on phones or a dialog on tablets. Analytics view name is CACHE_SETTINGS; ALLOWS_GUEST means logged-out users can open it.
## Key pieces
- `startActivity(context)` / `newStartIntent(context)`: the two launch helpers — WHY both: callers that just want to go use start, callers embedding the intent (notifications, tests) use newStartIntent.
- `onCreate` null-savedInstanceState guard: avoids stacking a second fragment on rotation when the system restores the first.
## Junior notes
- Thin-activity pattern again: no logic here by design — if you need to change cache-settings behavior, edit CacheSettingsFragment.
