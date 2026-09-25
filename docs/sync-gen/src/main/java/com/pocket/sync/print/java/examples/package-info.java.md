# sync-gen/src/main/java/com/pocket/sync/print/java/examples/package-info.java

## What this is

Declares this package as the configuration for generating from the sync-gen/examples schema: the worked example tying schema files to ExamplesConfig/ExamplesGenerator and the checked-in outputs. New team members start here to learn the codegen inputs-to-outputs loop on a small schema before facing Pocket's production one.

## How it fits

Points at examples.graphqls, queries.graphql, examples-usage.txt (inputs) and the output tree plus ExamplesGenerator (mechanics).

## Key pieces

- `example generation wiring` — the documented link between example inputs, config, and outputs

## Junior notes

- Learn codegen here first: the example schema exercises every major feature in miniature.
