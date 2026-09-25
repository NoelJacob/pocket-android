# sync/src/main/java/com/pocket/sync/source/AsyncPersisted.java

## What this is

The async flavor of Persisted: the same what-to-keep-in-Space controls (remember/forget/persist), delivered through PendingResult callbacks instead of blocking. Sources that persist over a slow store (SQLite on Android) implement this so cache management never stalls the caller.

## How it fits

SqliteBinaryStorage-backed spaces expose persistence through this shape, and AppSource coordinates with it when deciding which Things survive restarts. See Persisted for the blocking twin.

## Key pieces

- `async persist controls` — remember/forget/store operations that complete later via callbacks

## Junior notes

- Persistence here is about cache lifetime (what stays in the Space), not about syncing to the server; those are separate concerns.
