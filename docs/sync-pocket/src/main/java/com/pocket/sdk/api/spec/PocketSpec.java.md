# sync-pocket/src/main/java/com/pocket/sdk/api/spec/PocketSpec.java

## What this is

This is part of the sync spec engine: the rulebook that applies server results to local state and derives follow-on changes. It is pure logic with no networking, which makes sync behavior deterministic and testable.

## How it fits

Pocket invokes it after a Source returns: Applier writes server state into local Things, Deriver computes what else must change, and PocketSpec declares the rules they follow. SublistUtil is a small collection helper they share.

## Key pieces

- `PocketSpec` (class, line 9) — Pocket's {@link Spec}.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.

Names you will also see here: `PocketBaseSpec`, `Spec`.
