# sync/src/main/java/com/pocket/sync/source/AsyncSource.java

## What this is

The minimal async Source: a Source whose operations complete later and report through PendingResult callbacks rather than blocking. It is the parent concept that richer async contracts (AsyncClientSource, AsyncRemoteBackedSource, AsyncPersisted) extend with specific operations. SynchronousSource is its blocking counterpart.

## How it fits

Engine plumbing accepts this wherever work must not block, and concrete sources like AppSource implement the richer derived interfaces. Choose this level when you only need to say async without pinning down which operations.

## Key pieces

- `async callback contract` — marks operations as non-blocking with results delivered later via PendingResult

## Junior notes

- Async here means callback-based, not coroutines or Rx; the adapters in SyncExtensions/RxSync translate outward from this core.
