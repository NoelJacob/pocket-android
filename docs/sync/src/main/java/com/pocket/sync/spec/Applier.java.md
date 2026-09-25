# sync/src/main/java/com/pocket/sync/spec/Applier.java

## What this is

The narrowest piece of domain logic: something that can apply one Action's effect to a Space. Specs compose these (often one per action, generated into Applier helpers), and AppSource invokes them on the local-apply path so the UI updates before any network call. Unknown or custom actions get hand-written Applier logic in the concrete Spec subclass.

## How it fits

Generated *Applier classes (see ActionApplierGenerator and the test SyncTestsApplier) implement this per action; SyncTestsSpec shows the hand-written fallback for unknown actions. This is the seam where product behavior (what archive means) enters the engine.

## Key pieces

- `apply capability` — the single operation turning an Action plus a Space into updated Things

## Junior notes

- Product semantics live here, not in transports: if archiving does the wrong thing locally, the Applier (not the network) is at fault.
