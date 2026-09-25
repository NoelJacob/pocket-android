# sync/src/main/java/com/pocket/sync/space/mutable/MutableThing.java

## What this is

The mutable mirror of one Thing, used only inside MutableSpace: it keeps all field state in an internal ThingBuilder and stores references to other identifiable Things as nested MutableThings, so editing a shared child propagates to every parent that references it. The outside world still only ever sees frozen immutable Things; this mutability is a private performance and propagation mechanism, not a public editing API.

## How it fits

MutableSpace creates and manages these per stored Thing; generated Thing code supplies the MutableThing implementation each type needs (see Mutables for the contract). Reference sharing here is why one server update can refresh many screens at once.

## Key pieces

- `builder-held state` — all field values live in the internal builder so edits are cheap in-place writes
- `MutableThing references` — child links stay live, propagating one child's change to every referencing parent

## Junior notes

- Never hold or mutate these from app code: snapshot the immutable Thing out of the Space and leave the mutable mirror inside.
