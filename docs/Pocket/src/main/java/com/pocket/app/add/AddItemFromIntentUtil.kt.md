# Pocket/src/main/java/com/pocket/app/add/AddItemFromIntentUtil.kt
## What this is
The shared "save this shared URL" routine used by every intent entry point. Given a parsed `IntentItem`, it builds a sync-engine add action (URL, analytics context/time, optional title), checks the local cache for an existing unread copy, syncs, and reports the saved item plus a status (success, already-saved, invalid URL) through a callback. It is a Kotlin `object` (language-level singleton), not injected.
## How it fits
Called by `AddActivity.commitSave` with `Interaction.on(activity)` attribution and the `onSaved` callback; the same helper serves any other save-from-intent caller so duplicate detection and error mapping stay consistent. It talks only to the `Pocket` engine (`syncLocal` check, `sync` add) via the legacy `PocketApp` handle.
## Key pieces
- `add(intentItem, app, it, callback)`: null URL short-circuits to `ADD_INVALID_URL` without touching the engine.
- `isAlreadySaved` (`AtomicBoolean`): set from the `syncLocal` lookup (found AND unread), read in the `sync` success callback; WHY atomic is the two callbacks run on different threads.
- `Callback.result(item, status)` + `ErrorStatus`: null status means fresh save; `ADD_ALREADY_IN` vs `ADD_INVALID_URL` let callers pick the right toast; any sync failure maps to invalid URL (fail-soft to a message, never a crash).
- `ItemUtil.create(url, spec)`: builds the local item shell so the add targets a proper thing identity.
## Junior notes
- Both engine calls are async with listener callbacks, not suspend functions: the caller's activity may finish before they fire, so callbacks must not touch activity views (AddActivity's `onSaved` only toasts and finishes for this reason).
- Title is attached only when non-blank; an all-whitespace subject is treated as absent, not saved as an empty title.
