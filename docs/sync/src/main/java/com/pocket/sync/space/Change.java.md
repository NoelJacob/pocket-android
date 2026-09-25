# sync/src/main/java/com/pocket/sync/space/Change.java

## What this is

A tiny immutable pair describing one Thing update: previous (what the Space held) and latest (what replaced it). Observers and diff logic use it to answer did this actually change and what are the before/after values, without re-querying the Space. The type parameter keeps the pair homogeneous so no casting is needed downstream.

## How it fits

Diff and subscription delivery build these per updated Thing; UI callbacks compare previous versus latest to decide whether to re-render. For the observer-facing selector of what to watch, see Changes in the subscribe package (a different concept with a similar name).

## Key pieces

- `previous/latest` — the before/after pair that makes every update self-describing

## Junior notes

- Do not confuse this with Changes (the subscription selector): Change is one delivered update, Changes is what you subscribe with.
