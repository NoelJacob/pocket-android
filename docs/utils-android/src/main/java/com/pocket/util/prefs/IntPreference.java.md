# utils-android/src/main/java/com/pocket/util/prefs/IntPreference.java

## What this is

This is a tiny preferences building block: a typed wrapper (or store) around Android SharedPreferences so settings are read and written as booleans, ints, strings, or enums instead of raw untyped keys. Typed prefs turn typo-prone string keys into compiler-checked fields.

## How it fits

Feature code declares a `*Pref`/`*Preference` field (often via the Prefs registry) and reads/writes it directly; AndroidPrefStore is the SharedPreferences-backed store, MemoryPrefStore is the in-memory fake used in tests, and PrefixPreferences namespaces a group of keys.

## Key pieces

- `IntPreference` (interface, line 3) — core type of this file; callers reference it by name.
- `get` (fun, line 5) — The current value. If {@link #set(int)} has never been called, it returns the default value. Use {@link #isSet()} if you need to known.
- `set` (fun, line 6) — entry point other code calls; see callers for context.

## Junior notes

- Prefs read synchronously and are safe to call anywhere, but prefer the typed `*Pref` wrapper over raw string keys so typos fail at compile time.
