# sync-gen/src/main/java/com/pocket/sync/print/java/examples/ExamplesGenerator.java

## What this is

The runnable entry point producing the examples/output tree: main() wires ExamplesConfig through CommandLineGeneration/Generator over the example schema (examples.graphqls plus queries.graphql plus examples-usage.txt). Its output is checked in precisely so developers can read real emitted code without running codegen. Re-running it must reproduce the checked-in tree.

## How it fits

Driven by developers and CI when the example schema or emitters change; the package-info documents the input trio. If checked-in output differs from a fresh run, either the schema or an emitter changed without regenerating.

## Key pieces

- `main` — config-plus-schema wiring that regenerates the entire examples/output tree

## Junior notes

- Checked-in output is a snapshot, not source: edit the schema or the emitters, then regenerate, never patch outputs.
