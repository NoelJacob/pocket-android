# sync/src/main/java/com/pocket/sync/source/threads/ThreadPools.java

## What this is

The thread-policy interface the whole engine programs against: it creates the pools that Sources and Spaces run background work on, including prioritized pools whose PrioritizedRunnable tasks carry ordering weight. By depending on this interface instead of raw executors, the engine lets the surrounding app control thread counts, priorities, and tracking. Nested Pool/PrioritizedPool types describe the created pools.

## How it fits

Apps inject JavaThreadPools (tests/tools) or AndroidThreadPools (production Android) at source construction; engine code only ever sees this interface. If background sync work ever needs tracing or limits, this is the seam to add it.

## Key pieces

- `Pool/PrioritizedPool/PrioritizedRunnable` — the pool and task types expressing plain versus ordered background execution

## Junior notes

- Thread policy is app-owned: engine changes must not hard-code executors but go through the injected pools.
