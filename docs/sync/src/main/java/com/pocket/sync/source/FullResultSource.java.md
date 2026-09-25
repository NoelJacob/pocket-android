# sync/src/main/java/com/pocket/sync/source/FullResultSource.java

## What this is

A blocking Source that returns a full SyncResult: per-Thing and per-Action outcomes plus any resolved Things, instead of a bare success/failure. Sources that need action-by-action reporting (test fakes, transports that must surface partial failures) implement this so callers can see exactly which action failed and what the server returned for the rest.

## How it fits

AppSourceTest's fake remote implements this to echo scripted outcomes, and real transports build SyncResults the same way so AppSource.onRemoteResult can reconcile each action individually. It extends the SynchronousSource blocking style.

## Key pieces

- `syncFull` — the blocking call returning the detailed per-item SyncResult
- `Builder-recorded outcomes` — thing/action entries that let partial success survive instead of failing the whole batch

## Junior notes

- Partial success is normal here: always inspect the SyncResult for per-action failures rather than assuming all-or-nothing.
