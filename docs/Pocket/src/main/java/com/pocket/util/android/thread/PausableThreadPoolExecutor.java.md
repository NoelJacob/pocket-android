# Pocket/src/main/java/com/pocket/util/android/thread/PausableThreadPoolExecutor.java
## What this is
A `ThreadPoolExecutor` (a JDK work queue with a fixed set of background threads) that can pause before starting new work. `pause()` makes worker threads block in `beforeExecute()` on a lock; `resume()` wakes them. It also reports each run to an `ExecutionListener`.
For example, offline downloading pauses its pool while the user is on a metered action, and queued tasks wait instead of being dropped.

## How it fits
Direct base class of `TaskPool`, which wires itself as the `ExecutionListener` and adds submit/cancel-all semantics. `AppThreads` builds app pools (`WakefulTaskPool`, `PriorityTaskPool`) on top of this chain, so pause/resume propagates to every feature using those pools.

## Key pieces
- `pause()` / `resume()` / `isPaused()`: flip an `AtomicBoolean` and signal a `Condition`. WHY they exist: let the app suspend dispatch (e.g. at logout) without tearing the pool down.
- `beforeExecute(thread, runnable)`: blocks on `pauseLock` while paused. WHY it exists: the choke point where already-queued work waits.
- `afterExecute(runnable, throwable)`: unwraps `Future` failures (`CancellationException`/`ExecutionException`) and notifies the listener. WHY it exists: surfaces crashes and cancellations instead of swallowing them in the pool.
- `ExecutionListener` + `setExecutionListener()`: start/finish callbacks. WHY they exist: let `TaskPool` track active work and drive wake-lock/execution-state listeners.
- `shutdown()` override: resumes first so remaining tasks drain. WHY it exists: prevents a paused pool from hanging shutdown forever.

## Junior notes
- Pausing stops *starting* queued tasks; an already-running task continues until it finishes or checks cancellation itself.
- `ReentrantLock` + `Condition` here is a Java pause gate: `await()` parks the worker thread without spinning, and `signalAll()` wakes every parked worker at once.
