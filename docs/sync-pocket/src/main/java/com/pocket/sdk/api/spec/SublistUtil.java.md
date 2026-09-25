# sync-pocket/src/main/java/com/pocket/sdk/api/spec/SublistUtil.java

## What this is

This is part of the sync spec engine: the rulebook that applies server results to local state and derives follow-on changes. It is pure logic with no networking, which makes sync behavior deterministic and testable.

## How it fits

Pocket invokes it after a Source returns: Applier writes server state into local Things, Deriver computes what else must change, and PocketSpec declares the rules they follow. SublistUtil is a small collection helper they share.

## Key pieces

- `SublistUtil` (class, line 13) — Helper methods for working with {@link Thing}s that have count and offset support.
- `applyOffsetCount` (fun, line 15) — Return a sublist by applying offset and count to the list.
- `hasPreviousPages` (fun, line 48) — Checks if all of the items that will be needed to derive this list are available locally.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.

Names you will also see here: `Get`, `Space`, `Thing`, `Safe`.
