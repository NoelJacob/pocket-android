# sync/src/main/java/com/pocket/sync/source/result/SyncException.java

## What this is

The rich failure type for sync work: instead of a bare message it records which phase failed, the attempted request, what succeeded, and per-item Result entries for what did not. Helpers like statusOf, unwrap, getCause, and getUserFacingMessage let each layer take what it needs: retry logic reads statuses, UI reads the user-facing message. The nested Phase type names where in the pipeline things broke.

## How it fits

Transports throw these, AppSource catches them in onRemoteResult to reconcile partial batches, and error monitors log them. Because partial success is normal, callers must read the entries rather than treating any exception as total failure.

## Key pieces

- `Phase` — where the failure happened (local apply, send, parse, reconcile), guiding retry versus fix-forward decisions
- `statusOf/resultOf` — per-Thing/Action outcome lookups so callers retry exactly what failed
- `getUserFacingMessage/unwrap/getCause` — the UI-safe message plus unwrapping helpers to reach the root problem

## Junior notes

- Show users getUserFacingMessage, never getMessage: raw details may include payload fragments or non-actionable internals.
