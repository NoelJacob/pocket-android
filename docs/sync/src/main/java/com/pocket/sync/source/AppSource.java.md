# sync/src/main/java/com/pocket/sync/source/AppSource.java

## What this is

The production app data layer: a Source that owns its own local Space and syncs it with a remote source, tuned so local work stays instant and remote work never blocks it. Local applies happen immediately on the calling path (optimistic UI), remote sends are prioritized and batched in the background, and failures surface as SyncExceptions without losing the local state. Nested Transaction/Task types implement the internal queue that makes this ordering safe.

## How it fits

This is what the app actually constructs at startup (spec plus a MutableSpace plus a remote transport plus a Resolver) and what ViewModels talk to for saves, lists, and subscriptions. End to end, saving a URL walks through this file like this: the app builds a generated save Action (inputs: the URL) and hands it to the source, which applies it to the local Space first via the Spec so the UI updates instantly (this optimistic local apply is why saves feel immediate), then queues the Action for the remote transport; the server reply is parsed back into Things that are imprinted into the Space through the Resolver, and every subscriber watching those Things receives a Changes update and re-renders.

## Key pieces

- `spec/autoSendPriorityActions/autoSyncInvalidatedThings` — configuration: which Spec defines behavior, whether urgent actions jump the queue, whether stale Things refetch
- `syncLocal/syncRemote/sync` — the three sync scopes: local-only apply, remote-only flush, or the combined operation most callers want
- `transaction/run` — groups several operations into one atomic unit so the Space never shows a half-applied state
- `onRemoteResult/hasChangesSince/returnPending` — reconciles server replies into the Space and exposes what is still unsent
- `abandon/stopAllSubscriptions` — cancellation and teardown so background work and observers do not outlive their owner
- `Transaction/Task/ErrorMonitor` — the internal queue machinery that orders, retries, and reports on background sync work

## Junior notes

- Local-first is the whole point: if the UI ever waits on the network for a save to appear, something is misusing this class.
- Transactions plus auto-send interact: queue all related actions in one transaction so the remote sees them in the order the Space applied them.
