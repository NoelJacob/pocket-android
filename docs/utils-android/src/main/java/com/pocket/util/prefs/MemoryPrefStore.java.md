# utils-android/src/main/java/com/pocket/util/prefs/MemoryPrefStore.java

## What this is

This is a tiny preferences building block: a typed wrapper (or store) around Android SharedPreferences so settings are read and written as booleans, ints, strings, or enums instead of raw untyped keys. Typed prefs turn typo-prone string keys into compiler-checked fields.

## How it fits

Feature code declares a `*Pref`/`*Preference` field (often via the Prefs registry) and reads/writes it directly; AndroidPrefStore is the SharedPreferences-backed store, MemoryPrefStore is the in-memory fake used in tests, and PrefixPreferences namespaces a group of keys.

## Key pieces

- `MemoryPrefStore` (class, line 12) — core type of this file; callers reference it by name.
- `Get` (interface, line 28) — core type of this file; callers reference it by name.
- `changes` (fun, line 18) — entry point other code calls; see callers for context.
- `changes` (fun, line 22) — entry point other code calls; see callers for context.
- `get` (fun, line 29) — entry point other code calls; see callers for context.
- `put` (fun, line 32) — entry point other code calls; see callers for context.
- `keys` (fun, line 38) — entry point other code calls; see callers for context.
- `contains` (fun, line 43) — entry point other code calls; see callers for context.
- `remove` (fun, line 48) — entry point other code calls; see callers for context.
- `getString` (fun, line 54) — entry point other code calls; see callers for context.
- `set` (fun, line 59) — entry point other code calls; see callers for context.
- `stringChanges` (fun, line 64) — entry point other code calls; see callers for context.

## Junior notes

- Uses RxJava reactive streams (`Observable`/`Single`).
- Prefs read synchronously and are safe to call anywhere, but prefer the typed `*Pref` wrapper over raw string keys so typos fail at compile time.
- RxJava streams must be disposed/subcribed on the right scheduler; follow the existing `subscribeOn`/`observeOn` pattern in the file.
