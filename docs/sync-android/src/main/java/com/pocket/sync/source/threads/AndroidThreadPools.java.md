# sync-android/src/main/java/com/pocket/sync/source/threads/AndroidThreadPools.java

## What this is

The Android ThreadPools implementation: creates background pools whose threads run at Android's recommended background priority (quieter than foreground work, so scrolling and animation stay smooth). newPool/newPrioritizedPool create pools, submit queues work, priority/run shape prioritized execution, stop shuts down. It is the production counterpart to JavaThreadPools, which uses plain JVM defaults for tests and tools.

## How it fits

Injected at AppSource/Space construction in the Android app so all engine background work (local applies, remote sends, subscriber fan-out) inherits proper priorities. Tests keep using JavaThreadPools for determinism.

## Key pieces

- `newPool/submit/stop` — pool lifecycle with Android-tuned background thread priority
- `newPrioritizedPool/priority/run` — ordered pools letting urgent sync tasks jump the queue

## Junior notes

- Thread priority affects scheduling, not correctness: bugs that vanish under different priorities are ordering races, not fixes.
