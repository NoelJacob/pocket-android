# Pocket/src/main/java/com/pocket/util/java/StringBuilders.java
## What this is
A tiny synchronized pool that recycles `StringBuilder` objects to avoid allocating one per string-building burst. Call `get()` to borrow, `recycle()` to return (it clears first), and never touch a builder after recycling.
For example, `val b = StringBuilders.get(); try { ... } finally { StringBuilders.recycle(b); }` builds a report without leaving garbage for the GC.

## How it fits
Used on string-heavy paths: `Troubleshooter.getFormattedReport()` borrows a builder for the diagnostics dump, and `JavascriptFunction` builds JS call strings from a pooled builder. It is app-global (one static list guarded by `LOCK`), so any thread can borrow.

## Key pieces
- `get()`: pops a pooled builder or allocates when empty. WHY it exists: reuses buffers across calls instead of churning short-lived objects.
- `recycle(builder)`: resets then returns the builder to the pool. WHY it exists: makes the buffer available for the next borrower.
- `reset(builder)`: sets length to 0. WHY it exists: guarantees the next borrower starts with an empty buffer.
- `mBuilders` + `LOCK`: the pool list and its guard. WHY they exist: keep borrow/return safe across background threads.

## Junior notes
- Never use a builder after `recycle()`; it may already be handed to another thread, so copy out `toString()` first.
- Always recycle in a `finally` block, or an exception permanently leaks that buffer out of the pool.
- `StringBuilder` itself is not thread-safe; only the pool handoff is synchronized, so do not share one borrowed instance between threads.
