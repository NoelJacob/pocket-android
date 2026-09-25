# sync-pocket-android/src/main/java/com/pocket/sdk/api/source/LoggingPocket.java

## What this is

A debugging decorator around Pocket: wraps a real instance and logs calls as they happen, with presets (debug/debugCompact/dev/profiling/qa) tuning verbosity from full payloads to compact traces. Decorator means same interface, extra behavior: sync/syncRemote/syncLocal/syncActions/remember/forget/contains all log then delegate, and the Invoke/InvokeBind/Logger helper types shape the output. Explicitly not for production: full logging would leak user data and drown logcat. At ~441 lines it covers the whole Pocket surface.

## How it fits

Wrapped around the real Pocket in debug/QA builds (see AndroidPocket construction) so developers watch saves, syncs, and subscriptions live. Strip it (use the raw Pocket) for anything user-facing or performance-sensitive.

## Key pieces

- `debug/debugCompact/dev/profiling/qa` — verbosity presets from full-payload tracing to timing-focused profiling
- `sync/syncRemote/syncLocal/syncActions wrappers` — logged-then-delegated core operations
- `remember/forget/contains wrappers` — logged Space-lifetime operations for tracking holder leaks
- `Logger/Invoke/InvokeBind` — log-sink and call-description plumbing behind the presets

## Junior notes

- Never ship full-payload logging: payloads contain URLs, titles, and tokens that must not reach release logs.
- When a sync misbehaves in dev, enable the compact preset first: it shows ordering without burying you in payloads.
