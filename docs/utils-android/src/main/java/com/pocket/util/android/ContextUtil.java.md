# utils-android/src/main/java/com/pocket/util/android/ContextUtil.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `ContextUtil` (class, line 11) — s
- `getActivity` (fun, line 18) — Gets an Activity instance from a view's context using {@link #getActivity(android.content.Context)}.
- `getActivity` (fun, line 27) — Tries to cast a Context to an Activity. Can find even within ContextThemeWrapper. If context is null or the context is not an activity, returns null.
- `findContext` (fun, line 31) — entry point other code calls; see callers for context.
- `findContext` (fun, line 38) — Search this context and wrapped contexts for one that matches this class type

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.
