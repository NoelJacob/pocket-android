# sync-gen/src/main/java/com/pocket/sync/print/java/StandardModeller.java

## What this is

Default ValueModeller rules hand-written once for the common cases: nullable by default (isNullable), identity JSON mapping (toJson/json), trivial immutability (immutable), blank detection (isBlank), conversions (from/supportedConversions), boolean detection (isBoolean), and dangerous-value redaction (isDangerous/redact/unredact). Per-type configs override only what differs, so most values need no custom modeling at all.

## How it fits

Consulted by ModellerGenerator and PocketConfig/ExamplesConfig/SyncTestsConfig when deciding per-type emission; generated code assumes these defaults unless overridden.

## Key pieces

- `isNullable/toJson/immutable/isBlank` — the gentle defaults covering most value types
- `from/supportedConversions/isBoolean` — conversion and kind-detection rules
- `isDangerous/redact/unredact` — sensitive-value handling keeping secrets out of logs and snapshots

## Junior notes

- Defaults are load-bearing across every generated type: changing one regenerates behavior everywhere, so treat edits as engine-wide.
