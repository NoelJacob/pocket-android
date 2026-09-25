# Pocket/src/main/java/com/pocket/sdk2/api/legacy/MemoryLimitLogging.java
## What this is
A diagnostic helper that, when an `OutOfMemoryError` strikes, captures a one-line snapshot of app state: how many synced objects and items are in memory, plus JVM and Android memory counters. It wraps the original error in a `CaughtOomeError` whose message carries that snapshot, so crash reports tell us whether list size caused the OOM. The header notes it may go away once item-count limits are decided.
## How it fits
Called from catch sites around bulk sync/cache loads (anything that materializes many things at once). `gatherInfo()` pulls live counts from `Pocket` (`pocket.count(...)`) and memory stats from `Runtime`/`Debug.MemoryInfo`; `rethrowWithInfo()` attaches the snapshot and rethrows. Downstream the enriched error goes to the error reporter / crash logs.
## Key pieces
- `rethrowWithInfo(Throwable, thingCount, itemCount)` — wraps and rethrows with the snapshot in the message. WHY: preserves the original stack trace while adding the counts the crash reporter needs.
- `gatherInfo()` (no-arg) — best-effort live lookup of thing/item counts from `Pocket`, swallowing failures. WHY: call sites that do not already have counts.
- `gatherInfo(thingCount, itemCount)` — builds the CSV snapshot (counts, memory class, JVM heaps, PSS/summary stats), each section independently try/catched. WHY: partial info is better than another crash inside the crash handler.
- `CaughtOomeError` — plain `RuntimeException` wrapper carrying the snapshot as its message. WHY: searchable marker in crash logs distinguishing instrumented OOMs.
## Junior notes
- Every section is wrapped in try/catch-ignore: code running during an OOM must never allocate or throw — even building the string is done defensively.
- `Debug.getMemoryInfo()` values come from the OS and are informational only; `-1` fields mean that section failed, not zero usage.
