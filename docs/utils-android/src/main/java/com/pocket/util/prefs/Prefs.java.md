# utils-android/src/main/java/com/pocket/util/prefs/Prefs.java

## What this is

This is a tiny preferences building block: a typed wrapper (or store) around Android SharedPreferences so settings are read and written as booleans, ints, strings, or enums instead of raw untyped keys. Typed prefs turn typo-prone string keys into compiler-checked fields.

## How it fits

Feature code declares a `*Pref`/`*Preference` field (often via the Prefs registry) and reads/writes it directly; AndroidPrefStore is the SharedPreferences-backed store, MemoryPrefStore is the in-memory fake used in tests, and PrefixPreferences namespaces a group of keys.

## Key pieces

- `Prefs` (class, line 7) — core type of this file; callers reference it by name.
- `clearUser` (fun, line 18) — entry point other code calls; see callers for context.
- `clear` (fun, line 23) — entry point other code calls; see callers for context.
- `remove` (fun, line 29) — entry point other code calls; see callers for context.
- `appKeys` (fun, line 35) — entry point other code calls; see callers for context.
- `userKeys` (fun, line 40) — entry point other code calls; see callers for context.
- `forUser` (fun, line 47) — entry point other code calls; see callers for context.
- `forApp` (fun, line 52) — entry point other code calls; see callers for context.
- `forUser` (fun, line 58) — entry point other code calls; see callers for context.
- `forApp` (fun, line 63) — entry point other code calls; see callers for context.
- `forUser` (fun, line 69) — entry point other code calls; see callers for context.

## Junior notes

- Uses RxJava reactive streams (`Observable`/`Single`).
- Prefs read synchronously and are safe to call anywhere, but prefer the typed `*Pref` wrapper over raw string keys so typos fail at compile time.
- RxJava streams must be disposed/subcribed on the right scheduler; follow the existing `subscribeOn`/`observeOn` pattern in the file.
