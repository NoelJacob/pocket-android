# sync/src/main/java/com/pocket/sync/source/result/Status.java

## What this is

The outcome vocabulary for one Thing or Action during a sync: success, failure, discarded, and friends. Statuses ride inside Result entries and SyncExceptions so every layer agrees on what happened without parsing exception messages. Checking statusOf is how code asks what became of a specific action after a batch.

## How it fits

Sources record these via SyncResult.Builder as they work, and AppSource uses them to decide retries (failed) versus cleanup (discarded). Tests assert on them to prove success and failure paths distinctly.

## Key pieces

- `status values` — the fixed set of per-item outcomes every sync report uses

## Junior notes

- Compare Status values, never exception message text, when branching on sync outcomes; messages are for humans.
