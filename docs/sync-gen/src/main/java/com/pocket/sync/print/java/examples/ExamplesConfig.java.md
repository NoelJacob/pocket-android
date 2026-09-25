# sync-gen/src/main/java/com/pocket/sync/print/java/examples/ExamplesConfig.java

## What this is

The generation Config for the bundled example schema: per-type modeling rules (fromJson/fromParser/isBlank/compress/uncompress plus dangerous handling isDangerous/redact/unredact) showing how an API owner customizes value mapping. It is the worked example of Config specialization that PocketConfig follows at production scale. No CLI main here: ExamplesGenerator drives it.

## How it fits

Feeds Generator for the examples/output tree; authors copy its patterns when writing their own API config. Compare with SyncTestsConfig for the test-API variant.

## Key pieces

- `per-type modeling overrides` — worked examples of JSON, streaming, binary, and secrecy customization per value kind

## Junior notes

- Copy patterns from here, not generated output: configs are the maintained source, outputs are disposable artifacts.
