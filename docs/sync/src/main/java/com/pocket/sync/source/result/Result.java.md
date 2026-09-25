# sync/src/main/java/com/pocket/sync/source/result/Result.java

## What this is

One entry in a sync failure report: the outcome for a single Thing or Action inside a SyncException. A server round trip often half-succeeds (nine actions fine, one rejected), and this is the per-item record that says what happened to each. It pairs the item with its Status so callers can retry exactly the failures.

## How it fits

SyncException carries a list of these, and SyncResult.Builder accumulates them during syncFull so AppSource.onRemoteResult can reconcile item by item instead of treating the batch as all-or-nothing.

## Key pieces

- `per-item outcome` — which Thing/Action this entry is about and what its Status was

## Junior notes

- When a sync partially fails, iterate these entries rather than retrying the whole batch blindly.
