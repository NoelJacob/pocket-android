# utils/src/main/java/com/pocket/app/VersionUtil.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `VersionUtil` (class, line 8) — Help convert between versionCode and versionName based on how we format them for Pocket builds. See the Pocket build.gradle file for more details or the RELEASE
- `toVersionCode` (fun, line 10) — entry point other code calls; see callers for context.
- `toVersionCode` (fun, line 14) — entry point other code calls; see callers for context.
- `toVersionCode` (fun, line 18) — entry point other code calls; see callers for context.
- `toVersionName` (fun, line 22) — entry point other code calls; see callers for context.
- `extractNumber` (fun, line 35) — entry point other code calls; see callers for context.

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.
