# sync-gen/src/main/java/com/pocket/sync/print/java/ValueModeller.java

## What this is

The per-value-type modeling contract: instructions telling codegen how one schema Value maps to Java (nullability, JSON form, immutability, conversions, dangerous-ness). StandardModeller provides the gentle defaults; per-type configs (PocketConfig, ExamplesConfig, SyncTestsConfig) override specifics. ModellerGenerator follows these instructions when emitting creator and conversion code.

## How it fits

Implemented per value kind in the config classes; consulted during every Modeller emission. New scalar kinds start as a ValueModeller decision here.

## Key pieces

- `modeling instructions` — the per-type rules (shape, nullability, conversion, secrecy) codegen must honor

## Junior notes

- Model the value once here rather than special-casing per usage site: every Thing field of that type inherits the decision.
