# sync/src/main/java/com/pocket/sync/source/SynchronousRemoteBackedSource.java

## What this is

One interface combining blocking sync with remote backing: a SynchronousSource that is also a RemoteBackedSource. It is the blocking twin of AsyncRemoteBackedSource and the natural type for transports and tests that do request/response cycles on the calling thread. No new methods: the meaning is the combination.

## How it fits

Test fakes (FullResultSource in AppSourceTest) and simple transports implement this when async delivery would only add noise. Production UI paths prefer the async twin so the main thread never blocks.

## Key pieces

- `blocking plus remote-backed` — the combined contract for call-and-wait server round trips

## Junior notes

- Same main-thread rule as all blocking sources: use only from background threads or tests.
