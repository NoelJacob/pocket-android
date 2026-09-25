# utils-android/src/main/java/com/pocket/util/prefs/Store.java

## What this is

This is a tiny preferences building block: a typed wrapper (or store) around Android SharedPreferences so settings are read and written as booleans, ints, strings, or enums instead of raw untyped keys. Typed prefs turn typo-prone string keys into compiler-checked fields.

## How it fits

Feature code declares a `*Pref`/`*Preference` field (often via the Prefs registry) and reads/writes it directly; AndroidPrefStore is the SharedPreferences-backed store, MemoryPrefStore is the in-memory fake used in tests, and PrefixPreferences namespaces a group of keys.

## Key pieces

- `Store` (interface, line 13) — A store of preference values. Typically persisted.
- `changes` (fun, line 14) — entry point other code calls; see callers for context.
- `contains` (fun, line 16) — entry point other code calls; see callers for context.
- `remove` (fun, line 17) — entry point other code calls; see callers for context.
- `clear` (fun, line 18) — entry point other code calls; see callers for context.
- `keys` (fun, line 19) — entry point other code calls; see callers for context.
- `getString` (fun, line 21) — entry point other code calls; see callers for context.
- `set` (fun, line 22) — entry point other code calls; see callers for context.
- `stringChanges` (fun, line 23) — entry point other code calls; see callers for context.
- `getStringSet` (fun, line 26) — @return null if not present or an immutable set.
- `set` (fun, line 28) — Update the value. Note: Implementations to take care to make sure that changes to the value passed here don't change the internal stored value. Make a copy of t

## Junior notes

- Uses RxJava reactive streams (`Observable`/`Single`).
- Uses Android SharedPreferences (persistent key-value storage).
- Prefs read synchronously and are safe to call anywhere, but prefer the typed `*Pref` wrapper over raw string keys so typos fail at compile time.
