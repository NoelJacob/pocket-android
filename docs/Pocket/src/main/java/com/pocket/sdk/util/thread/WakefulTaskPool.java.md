# Pocket/src/main/java/com/pocket/sdk/util/thread/WakefulTaskPool.java
## What this is
A background thread pool that holds a wake lock (an Android reservation that keeps the CPU on when the screen is off) whenever it is executing or has pending work. It wraps every pool with a `WakeLockHolder` whose timeout debug string reports queue size, active count, and idle time. When the pool goes idle it releases the lock.
## How it fits
The parent of `PriorityTaskPool` and the base for pools created by app components via Hilt-provided `WakeLockManager`. Task submission flows through `TaskPool`; the execution-state listener here calls `wakelocks.acquire()` when work starts and `wakelocks.release()` when it drains, and `WakefulAppService` keeps the process alive while locks are held.
## Key pieces
- `WakefulTaskPool(wakelocks, sizes..., poolName)` constructors — fixed, core/max, queue, and keep-alive variants mirroring `TaskPool`. WHY: any pool shape can be made wakeful without changing call sites.
- `init(wakelocks)` — creates a 30-minute-timeout holder named after the pool with a debug snapshot (queue peek/size, active/completed/total counts, minutes since submit, has-work/active/paused flags) and registers the execution-state listener. WHY: leaked locks identify exactly which pool and what it was stuck on.
- Execution-state listener — acquire on executing, release on idle. WHY: the lock precisely covers "work exists", including time spent queued, not just time on a thread.
## Junior notes
- The 30/50 numbers are warn/stop timeout minutes for safety, not scheduling: always let the pool drain normally rather than depending on the timeout.
- Pools created without going through this class hold no wake lock; background work added on a plain pool can be killed with the screen off, so default to this (or `PriorityTaskPool`) for work that must finish.
