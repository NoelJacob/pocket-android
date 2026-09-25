# sync-pocket-android/src/test/java/com/pocket/sync/source/AppSourceTest.java

## What this is

AppSource concurrency proof: await() builds an AppSource over a random-latency fake remote (each syncFull sleeps up to 500ms then reports per-action SUCCESS via SyncResult.Builder) and asserts completions resolve correctly. The randomness is the point: it shakes out ordering races between local apply and remote reconcile that deterministic tests miss.

## How it fits

Guards the AppSource internal queue (Transaction/Task machinery) against real timing skew; also demonstrates the FullResultSource fake pattern. Flaky failures here are real race reports, not noise.

## Key pieces

- `await` — random-latency remote round trips completing with correct per-action outcomes

## Junior notes

- Never fix a flaky run by removing the randomness: the jitter is the test's only way to explore interleavings.
