# sync-gen/src/main/java/com/pocket/sync/print/java/pocket/PocketConfig.java

## What this is

Pocket's production Config: the full per-type modeling for the real API (fromJson/fromParser/compress/uncompress overrides, isBlank rules, graphQlType mappings, isBoolean detection) at ~623 lines because the real API has many special scalars and legacy encodings. It subclasses Config the way ExamplesConfig demonstrates in miniature. Every Pocket codegen run applies these rules.

## How it fits

Feeds AndroidClassGenerator's production runs; emitted Pocket generated classes (sdk.api.generated) reflect these choices. API evolution lands here as modeling updates plus schema edits.

## Key pieces

- `per-type production modeling` — JSON/streaming/binary mappings for every Pocket scalar and value kind
- `graphQlType/isBoolean` — operation-type mapping plus boolean-kind detection for codegen branching

## Junior notes

- Production modeling choices are compat commitments: changing a mapping reshapes generated parsing for every user, so version and migrate deliberately.
