# sync/src/main/java/com/pocket/sync/action/Time.java

## What this is

A tiny comparable wrapper around a moment in time used to stamp Actions. It deliberately does not fix a unit: each implementation decides whether the value means seconds, milliseconds, or nanoseconds past epoch, so different server APIs can keep their native convention. Being Comparable lets the engine order actions by time when syncing and resolving conflicts.

## How it fits

Action.time() returns one of these, and sources/specs compare them to decide ordering (for example which of two edits wins). It flows wherever Actions flow: local apply, the send queue, and conflict handling.

## Key pieces

- `compareTo` — orders two timestamps so the engine can sort actions and pick winners deterministically

## Junior notes

- Always check which unit a given API uses before comparing or displaying a Time value; mixing seconds and milliseconds is the classic bug here.
