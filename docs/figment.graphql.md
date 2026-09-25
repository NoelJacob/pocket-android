# figment.graphql

## What this is
This file defines the custom GraphQL directives (annotations starting with `@`) that power the entire sync code generator. It declares building blocks like `@figment` (field mapping, auth, priority, effect, v3/client aliases), `@derives` (computed fields with remap and reactive rules), `@remote`/`@base_action` (schema-level sync wiring), and identity markers (`@id`, `@unique`, `@hash_target`, `@local`, `@root_value`, `@map`, `@variety`, `@enum_value`, `@extend`). It contains no business types — only the vocabulary other schema files use to describe themselves.

## How it fits
Every schema under `sync-pocket/src/main/graphql/*.graphqls` and the `sync-gen` examples/tests annotate their types with these directives; `sync-parser` reads them into an in-memory model ("figments") and `sync-gen` emits the Java sync classes (`*Spec`, `*Things`, actions, modellers) that `sync`, `sync-pocket`, and the app compile against. `graphql.config.yml` includes this file in all three projects (prod, test, examples) so IDE validation resolves the directives everywhere. Changing a directive here changes what the generator understands across the whole engine.

## Key pieces
- **`@figment` (auth, address, priority, effect, v3_alias, client_api_alias, …)** — the master annotation linking one schema field to its sync semantics and legacy API names; the reason old v3 fields and new graph fields can coexist.
- **`@derives` (first_available, remap, reactive, instructions)** — declares computed fields and their fallback/remap logic so generated derivers know what to recompute on change.
- **`@remote` / `@base_action` / `@base_action_field`** — schema-level declarations of remote endpoints and the base mutation vocabulary every spec inherits.
- **Identity and storage markers (`@id`, `@unique`, `@hash_target`, `@local`, `@root_value`, `@map`, `@variety`, `@enum_value`, `@extend`)** — the fine print telling the generator what identifies a thing, what is hashed, what stays device-local, and how enums and extensions map.

## Junior notes
- Directives are metadata about the schema, not queries — you never send them to a server; the generator consumes them at build time.
- If your IDE flags `@figment` as unknown in a schema file, the GraphQL plugin from `.idea/externalDependencies.xml` is missing, not the schema.
