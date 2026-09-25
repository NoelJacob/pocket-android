# sync/src/main/java/com/pocket/sync/source/result/RemotePriority.java

## What this is

A small hint enum telling a remote-backed source how urgently a locally-applied Action should be sent to the server. Spec.apply returns one of these per action: send-it-now for things the user is waiting on (a save), versus whenever for background bookkeeping that can batch. fromKey restores the value from its serialized form.

## How it fits

Every Action declares its priority, AppSource's send queue orders by it (see autoSendPriorityActions), and transports may batch low-priority actions while flushing urgent ones immediately.

## Key pieces

- `priority values` — the urgency levels from send-now down to background-batch
- `fromKey` — deserializes a stored priority back to its enum value

## Junior notes

- Priority is a hint, not a guarantee: offline or failing remotes still queue everything, urgent or not.
