# utils-android/src/main/java/com/pocket/util/prefs/Preference.java

## What this is

This is a tiny preferences building block: a typed wrapper (or store) around Android SharedPreferences so settings are read and written as booleans, ints, strings, or enums instead of raw untyped keys. Typed prefs turn typo-prone string keys into compiler-checked fields.

## How it fits

Feature code declares a `*Pref`/`*Preference` field (often via the Prefs registry) and reads/writes it directly; AndroidPrefStore is the SharedPreferences-backed store, MemoryPrefStore is the in-memory fake used in tests, and PrefixPreferences namespaces a group of keys.

## Key pieces

- `Preference` (interface, line 16) — Represents a value, of some type, typically persisted across app lifecycles / processes.
- `isSet` (fun, line 18) — @return true if this preference as been explicitly changed/set in the past, false if it has not and is returning its defaultValue.
- `changes` (fun, line 20) — An observable anytime this preference's value changes in the future
- `getWithChanges` (fun, line 22) — An observable that emits the current value on subscribe plus anytime this preference's value changes in the future.

## Junior notes

- Uses RxJava reactive streams (`Observable`/`Single`).
- Prefs read synchronously and are safe to call anywhere, but prefer the typed `*Pref` wrapper over raw string keys so typos fail at compile time.
- RxJava streams must be disposed/subcribed on the right scheduler; follow the existing `subscribeOn`/`observeOn` pattern in the file.
