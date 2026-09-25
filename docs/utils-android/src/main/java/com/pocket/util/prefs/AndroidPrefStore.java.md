# utils-android/src/main/java/com/pocket/util/prefs/AndroidPrefStore.java

## What this is

This is a tiny preferences building block: a typed wrapper (or store) around Android SharedPreferences so settings are read and written as booleans, ints, strings, or enums instead of raw untyped keys. Typed prefs turn typo-prone string keys into compiler-checked fields.

## How it fits

Feature code declares a `*Pref`/`*Preference` field (often via the Prefs registry) and reads/writes it directly; AndroidPrefStore is the SharedPreferences-backed store, MemoryPrefStore is the in-memory fake used in tests, and PrefixPreferences namespaces a group of keys.

## Key pieces

- `AndroidPrefStore` (class, line 14) — A {@link Store} backed by Android {@link SharedPreferences}
- `Get` (interface, line 40) — core type of this file; callers reference it by name.
- `changes` (fun, line 23) — entry point other code calls; see callers for context.
- `changes` (fun, line 34) — entry point other code calls; see callers for context.
- `get` (fun, line 41) — entry point other code calls; see callers for context.
- `contains` (fun, line 45) — entry point other code calls; see callers for context.
- `remove` (fun, line 50) — entry point other code calls; see callers for context.
- `keys` (fun, line 55) — entry point other code calls; see callers for context.
- `getString` (fun, line 60) — entry point other code calls; see callers for context.
- `set` (fun, line 65) — entry point other code calls; see callers for context.
- `stringChanges` (fun, line 70) — entry point other code calls; see callers for context.
- `getStringSet` (fun, line 76) — entry point other code calls; see callers for context.

## Junior notes

- Uses RxJava reactive streams (`Observable`/`Single`).
- Uses Android SharedPreferences (persistent key-value storage).
- Prefs read synchronously and are safe to call anywhere, but prefer the typed `*Pref` wrapper over raw string keys so typos fail at compile time.
