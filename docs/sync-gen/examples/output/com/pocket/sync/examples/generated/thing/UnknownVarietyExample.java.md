# sync-gen/examples/output/com/pocket/sync/examples/generated/thing/UnknownVarietyExample.java

## What this is

Checked-in codegen output (never edit by hand): this file shows forward-compatible handling of unknown union members. Like all files under examples/output, it is produced from the example schema (examples.graphqls plus queries.graphql plus examples-usage.txt) by running ExamplesGenerator, and it exists so developers can read real emitted code without running codegen themselves.

## How it fits

Regenerated wholesale by ExamplesGenerator whenever the example schema or any sync-gen emitter changes; the emitting generator (ThingGenerator, ActionGenerator, EnumGenerator, and friends) owns its shape, and the checked-in copy is a snapshot for reading and diffing. If a fresh run differs from this file, the schema or an emitter changed without regenerating.

## Key pieces

- `regenerated file (do not edit)` — reproduced byte-for-byte by rerunning ExamplesGenerator; edits here are lost on the next run
- `graphQl()` — one emitted accessor/field of this generated type, mirroring its schema declaration
- `auth()` — one emitted accessor/field of this generated type, mirroring its schema declaration
- `type()` — one emitted accessor/field of this generated type, mirroring its schema declaration
- `remote()` — one emitted accessor/field of this generated type, mirroring its schema declaration
- `getCreator()` — one emitted accessor/field of this generated type, mirroring its schema declaration
- `getStreamingCreator()` — one emitted accessor/field of this generated type, mirroring its schema declaration

## Junior notes

- Snapshot, not source: fix the schema or the emitter, then regenerate; never patch this file.
- Use these outputs to learn what each schema feature produces before reading the emitter code that produces it.
