# sync-gen/src/main/java/com/pocket/sync/print/java/DerivedSpecGenerator.java

## What this is

Emits the derivation helper class: typed derive methods per derived field (classname names the class, deriveMethod emits each method) that the concrete spec's Deriver extends. Derived fields are values computed from other fields (a display title from title-or-URL, for example); the generated helper supplies signatures and dispatch while hand-written code supplies the computation. The generated class's own javadoc documents the contract.

## How it fits

Runs inside Generator.generate(); SyncTestsSpec.Deriver (extending SyncTestsDerives) is the canonical hand-written counterpart. Reactions gathering decides when these run.

## Key pieces

- `classname/deriveMethod` — helper class naming plus per-derived-field method emission

## Junior notes

- Derived methods must be pure functions of their inputs: side effects here recompute unpredictably as diffs arrive.
