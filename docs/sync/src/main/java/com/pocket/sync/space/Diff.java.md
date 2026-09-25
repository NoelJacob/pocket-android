# sync/src/main/java/com/pocket/sync/space/Diff.java

## What this is

An immutable report of what changed across a batch of Things: which were added or changed (added/changed sets, find/all lookups, isEmpty check), which were invalidated for refetch (setInvalidated), and currentValues snapshots for comparison. The nested Builder lets producers accumulate entries during apply/imprint and freeze them at the end. Reactions and derive logic read diffs to decide what must be recomputed.

## How it fits

Spec.apply and the imprint path produce these; MutableSpace.derive and Thing.reactions consume them to recompute only affected derived fields. SpaceTest's diffIncludesEffectedReferences proves reference changes propagate.

## Key pieces

- `added/changed/find/all/isEmpty` — querying which Things the batch touched
- `setInvalidated` — marking Things stale so the source refetches them from the remote
- `currentValues` — snapshot access for comparing new state against old
- `Builder (add/build)` — the accumulation API producers use before freezing the report

## Junior notes

- Derived fields must key off the Diff, not re-scan the Space: the diff is the complete list of what could have affected them.
