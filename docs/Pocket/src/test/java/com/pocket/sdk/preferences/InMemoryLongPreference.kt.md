# Pocket/src/test/java/com/pocket/sdk/preferences/InMemoryLongPreference.kt
## What this is
One-line test fake: a `LongPref` (a persisted long value, e.g. timestamps) backed by `MemoryPrefStore` (an in-memory map instead of disk). Gives session and time tests durable-looking storage without touching device preferences.
## How it fits
Used by `SessionShould` (session id plus timestamps) and similar tests needing `LongPreference` persistence. The fake exists because real prefs need Android `SharedPreferences`; in-memory storage is faster and isolated per test.
## Key pieces
- `InMemoryLongPreference : LongPref("key", 0L, MemoryPrefStore())` — fixed key, zero default, memory store; WHY: minimal long-valued pref double.
## Junior notes
- Each instance gets its own `MemoryPrefStore`, so values do not leak between tests — create a new one per test.
- Key is hardcoded to one value; tests needing two independent longs must use distinct mechanisms (e.g. real `Prefs` with named prefs).
