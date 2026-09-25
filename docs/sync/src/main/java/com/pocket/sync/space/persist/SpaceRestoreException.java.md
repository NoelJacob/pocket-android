# sync/src/main/java/com/pocket/sync/space/persist/SpaceRestoreException.java

## What this is

The startup-failure signal: thrown when a Space cannot restore the persisted state it needs to function, whether from a transient error worth retrying or a missing/corrupt store. It forces the app to decide explicitly (retry, wipe-and-refetch, or fail loudly) instead of limping along on a half-loaded cache that looks fine but is not.

## How it fits

DumbStorage.restore paths raise this; app startup code catches it around Space initialization and picks a recovery policy. Error monitors should log it with the store identity attached.

## Key pieces

- `restore-failure signal` — the typed trigger for retry-versus-wipe recovery decisions at startup

## Junior notes

- Never swallow this and continue on an empty Space silently: users would see blank lists with no error and no path to recovery.
