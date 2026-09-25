# utils-android/src/main/java/com/pocket/util/prefs/PrefixPreferences.java

## What this is

This is a tiny preferences building block: a typed wrapper (or store) around Android SharedPreferences so settings are read and written as booleans, ints, strings, or enums instead of raw untyped keys. Typed prefs turn typo-prone string keys into compiler-checked fields.

## How it fits

Feature code declares a `*Pref`/`*Preference` field (often via the Prefs registry) and reads/writes it directly; AndroidPrefStore is the SharedPreferences-backed store, MemoryPrefStore is the in-memory fake used in tests, and PrefixPreferences namespaces a group of keys.

## Key pieces

- `PrefixPreferences` (class, line 13) — A {@link Preferences} that wraps another and when getting preference instances, it adds a prefix to the key and invokes it on the parent.
- `prefix` (fun, line 23) — entry point other code calls; see callers for context.
- `remove` (fun, line 28) — entry point other code calls; see callers for context.
- `userKeys` (fun, line 33) — entry point other code calls; see callers for context.
- `appKeys` (fun, line 43) — entry point other code calls; see callers for context.
- `clearUser` (fun, line 53) — entry point other code calls; see callers for context.
- `clear` (fun, line 60) — entry point other code calls; see callers for context.
- `forUser` (fun, line 68) — entry point other code calls; see callers for context.
- `forApp` (fun, line 73) — entry point other code calls; see callers for context.
- `forUser` (fun, line 78) — entry point other code calls; see callers for context.
- `forApp` (fun, line 83) — entry point other code calls; see callers for context.

## Junior notes

- Uses RxJava reactive streams (`Observable`/`Single`).
- Prefs read synchronously and are safe to call anywhere, but prefer the typed `*Pref` wrapper over raw string keys so typos fail at compile time.
- RxJava streams must be disposed/subcribed on the right scheduler; follow the existing `subscribeOn`/`observeOn` pattern in the file.
