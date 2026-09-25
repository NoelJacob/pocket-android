# utils-android/src/main/java/com/pocket/util/prefs/BooleanPref.java

## What this is

This is a tiny preferences building block: a typed wrapper (or store) around Android SharedPreferences so settings are read and written as booleans, ints, strings, or enums instead of raw untyped keys. Typed prefs turn typo-prone string keys into compiler-checked fields.

## How it fits

Feature code declares a `*Pref`/`*Preference` field (often via the Prefs registry) and reads/writes it directly; AndroidPrefStore is the SharedPreferences-backed store, MemoryPrefStore is the in-memory fake used in tests, and PrefixPreferences namespaces a group of keys.

## Key pieces

- `BooleanPref` (class, line 7) — Implementation of {@link BooleanPreference}
- `get` (fun, line 20) — entry point other code calls; see callers for context.
- `set` (fun, line 25) — entry point other code calls; see callers for context.
- `isSet` (fun, line 30) — entry point other code calls; see callers for context.
- `changes` (fun, line 35) — entry point other code calls; see callers for context.
- `getWithChanges` (fun, line 40) — entry point other code calls; see callers for context.

## Junior notes

- Uses RxJava reactive streams (`Observable`/`Single`).
- Prefs read synchronously and are safe to call anywhere, but prefer the typed `*Pref` wrapper over raw string keys so typos fail at compile time.
- RxJava streams must be disposed/subcribed on the right scheduler; follow the existing `subscribeOn`/`observeOn` pattern in the file.
