# Pocket/src/main/java/com/pocket/util/android/thread/PriorityFutureTaskComparator.java
## What this is
The sort rule for the priority work queue: higher `getPriority()` first, and for ties the smaller `getAddedOrder()` (earlier submission) first. It is a `Comparator<Runnable>` used by a `PriorityBlockingQueue`.
For example, with a HIGH task submitted 5th and a LOW task submitted 1st, this orders HIGH first despite arriving later.

## How it fits
Installed by `PriorityTaskPool` constructors (`new PriorityBlockingQueue<>(11, new PriorityFutureTaskComparator())`), which `AppThreads.newPriorityPool()` and offline downloading (`offline-coord`, `workers` pools) use. It only ever sees `PriorityFutureTask` instances enqueued via `TaskPool.submit()`.

## Key pieces
- `compare(a, b)`: priority-descending, then FIFO-ascending via `Long.signum` differences. WHY it exists: encodes "most important first, oldest first among equals" as one queue ordering.

## Junior notes
- `Long.signum(diff)` returns just -1/0/1, which is what a comparator must return; the raw difference itself could overflow an int.
- Because it casts to `PriorityFutureTask`, never submit raw `Runnable`s to a pool built with this comparator.
