# utils-android/src/main/java/com/pocket/util/prefs/Preferences.java

## What this is

This is a tiny preferences building block: a typed wrapper (or store) around Android SharedPreferences so settings are read and written as booleans, ints, strings, or enums instead of raw untyped keys. Typed prefs turn typo-prone string keys into compiler-checked fields.

## How it fits

Feature code declares a `*Pref`/`*Preference` field (often via the Prefs registry) and reads/writes it directly; AndroidPrefStore is the SharedPreferences-backed store, MemoryPrefStore is the in-memory fake used in tests, and PrefixPreferences namespaces a group of keys.

## Key pieces

- `Preferences` (interface, line 17) — An app's persisted {@link Preference}s.
- `clearUser` (fun, line 20) — Reset all user based preferences, returning them back to unset.
- `clear` (fun, line 22) — Reset all preferences, returning them back to unset.
- `remove` (fun, line 24) — Reset the preference with this key, returning it back to unset.
- `userKeys` (fun, line 26) — A set of all known keys for user preferences
- `appKeys` (fun, line 28) — A set of all known keys for app preferences
- `forUser` (fun, line 30) — entry point other code calls; see callers for context.
- `forApp` (fun, line 31) — entry point other code calls; see callers for context.
- `forUser` (fun, line 33) — entry point other code calls; see callers for context.
- `forApp` (fun, line 34) — entry point other code calls; see callers for context.
- `forUser` (fun, line 36) — entry point other code calls; see callers for context.

## Junior notes

- Uses RxJava reactive streams (`Observable`/`Single`).
- Prefs read synchronously and are safe to call anywhere, but prefer the typed `*Pref` wrapper over raw string keys so typos fail at compile time.
- RxJava streams must be disposed/subcribed on the right scheduler; follow the existing `subscribeOn`/`observeOn` pattern in the file.
