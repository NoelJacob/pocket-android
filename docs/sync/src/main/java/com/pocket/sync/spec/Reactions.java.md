# sync/src/main/java/com/pocket/sync/spec/Reactions.java

## What this is

The collection box for reactive recomputation: during Thing.reactions, the engine gathers which Things and derived fields must be rederived because something they listen to changed, and this object accumulates exactly that set. The thing/things/typesAndFields/thingsAndFields query methods let the derive pass read back the work list in whatever grouping is convenient. Without it, reactive fields would either miss updates or recompute the whole Space.

## How it fits

MutableSpace.derive consumes one of these after every imprint/apply; generated derive helpers (SyncTestsDerives pattern) fill it via reactions() implementations. SpaceTest's reactiveTo* tests prove the gathering is precise.

## Key pieces

- `thing/things/typesAndFields/thingsAndFields` — grouped views of what must be rederived after a change

## Junior notes

- Reactions gathering must be complete but minimal: missing entries cause stale UI, extra entries cause wasted recompute on every keystroke.
