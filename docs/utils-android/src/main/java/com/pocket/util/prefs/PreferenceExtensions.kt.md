# utils-android/src/main/java/com/pocket/util/prefs/PreferenceExtensions.kt

## What this is

This is a tiny preferences building block: a typed wrapper (or store) around Android SharedPreferences so settings are read and written as booleans, ints, strings, or enums instead of raw untyped keys. Typed prefs turn typo-prone string keys into compiler-checked fields.

## How it fits

Feature code declares a `*Pref`/`*Preference` field (often via the Prefs registry) and reads/writes it directly; AndroidPrefStore is the SharedPreferences-backed store, MemoryPrefStore is the in-memory fake used in tests, and PrefixPreferences namespaces a group of keys.

## Key pieces

- `BooleanPreference` (fun, line 5) — entry point other code calls; see callers for context.
- `BooleanPreference` (fun, line 6) — entry point other code calls; see callers for context.
- `FloatPreference` (fun, line 15) — entry point other code calls; see callers for context.
- `FloatPreference` (fun, line 16) — entry point other code calls; see callers for context.
- `IntPreference` (fun, line 18) — entry point other code calls; see callers for context.
- `IntPreference` (fun, line 19) — entry point other code calls; see callers for context.
- `LongPreference` (fun, line 21) — entry point other code calls; see callers for context.
- `LongPreference` (fun, line 22) — entry point other code calls; see callers for context.
- `StringPreference` (fun, line 24) — entry point other code calls; see callers for context.
- `StringPreference` (fun, line 25) — entry point other code calls; see callers for context.

## Junior notes

- Prefs read synchronously and are safe to call anywhere, but prefer the typed `*Pref` wrapper over raw string keys so typos fail at compile time.
