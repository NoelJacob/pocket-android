# sync/src/main/java/com/pocket/sync/space/mutable/Mutables.java

## What this is

The contract generated Thing code must satisfy so MutableSpace can manage MutableThings: the method set each generated type's mutable implementation needs in order to plug into storage, change tracking, and snapshotting. It is engine-to-codegen plumbing, not something app or feature code calls directly. When codegen adds a new field kind, this interface is where the mutable-side support requirement is declared.

## How it fits

ThingGenerator emits implementations of this per generated Thing; MutableSpace programs against the interface so it never depends on generated classes directly. If generated code and the engine disagree at runtime, version skew between these two is a prime suspect.

## Key pieces

- `mutable-support methods` — the per-type hooks for state access, change flags, and immutable snapshots

## Junior notes

- App code should never reference this: if you find yourself needing it, you are reaching past the Space abstraction.
