# sync/src/main/java/com/pocket/sync/source/SynchronousSource.java

## What this is

The minimal blocking Source: a Source with a blocking sync implementation, meaning the calling thread waits until the work finishes. It is the counterpart to AsyncSource and the parent of SynchronousRemoteBackedSource and ClientSource. Blocking APIs are simpler to reason about and ideal for tests and background workers.

## How it fits

Background sync jobs and unit tests use blocking sources directly, while UI code prefers the async variants. InMemorySource and FullResultSource are typical simple implementations.

## Key pieces

- `blocking sync` — the caller waits for completion, so results and exceptions surface on the calling thread

## Junior notes

- Never invoke blocking sync on Android's main thread; the UI would freeze until the server round trip finishes.
