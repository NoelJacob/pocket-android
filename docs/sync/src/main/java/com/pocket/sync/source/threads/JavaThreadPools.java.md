# sync/src/main/java/com/pocket/sync/source/threads/JavaThreadPools.java

## What this is

The plain-JVM ThreadPools implementation: creates background pools with ordinary Java thread defaults, plus prioritized variants whose tasks carry ordering weight. submit queues work, newPool/newPrioritizedPool create pools, priority/run shape the prioritized execution, and stop shuts pools down. Unit tests and desktop tooling use this; Android uses AndroidThreadPools instead.

## How it fits

Sources receive a ThreadPools at construction so the app architecture (not the engine) owns thread policy; engine internals submit local/remote work through it. AppSourceTest wires this up with JavaThreadPools plus a calling-thread Publisher.

## Key pieces

- `newPool/submit/stop` — pool lifecycle and work submission with standard Java thread settings
- `newPrioritizedPool/priority/run` — pools that order queued sync tasks so urgent actions jump ahead

## Junior notes

- Java defaults mean no Android thread priority or main-thread affinity: never use this in the Android app itself.
