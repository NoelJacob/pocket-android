# sync/src/main/java/com/pocket/sync/spec/Syncable.java

## What this is

The common supertype of Thing and Action: anything the engine can sync. The nested name type carries its operation identity. Generic code (SyncResult entries, parsers, Modeller helpers) programs against this when it does not care whether it holds state or intent. NO_ALIASES (referenced across tests) is the neutral JSON mapping used when no remote naming applies.

## How it fits

Parsers, serializers, and result reports accept Syncable so one code path handles both halves of the domain. Thing and Action each add their own half-specific behavior on top.

## Key pieces

- `shared syncable contract` — the operations meaningful for both state and intent, such as naming and JSON conversion
- `name` — the operation identity shared by queries, mutations, things, and actions

## Junior notes

- Reach for Thing/Action types when you need their specifics; use this only for genuinely half-agnostic plumbing.
