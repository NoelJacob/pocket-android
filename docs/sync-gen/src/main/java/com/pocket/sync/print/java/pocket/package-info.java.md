# sync-gen/src/main/java/com/pocket/sync/print/java/pocket/package-info.java

## What this is

Declares this package as Pocket's production sync-engine generation configuration: PocketConfig plus AndroidClassGenerator turning Pocket's schemas into the app's generated API. It separates production codegen inputs from the examples (learning) and tests (contract) variants.

## How it fits

Entry documentation for the production generation path; see AndroidClassGenerator to run it and PocketConfig for its rules.

## Key pieces

- `production generation wiring` — the documented link between Pocket schemas, config, and shipped generated code

## Junior notes

- Production, examples, and tests each keep their own config: shared tweaks belong in shared emitters, not copied across configs.
