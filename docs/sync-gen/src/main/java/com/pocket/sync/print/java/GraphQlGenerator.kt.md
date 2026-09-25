# sync-gen/src/main/java/com/pocket/sync/print/java/GraphQlGenerator.kt

## What this is

Emits the GraphQL operation support: per-operation documents and helpers (operation/graphQl builders) plus addGraphQlSupport, which attaches GraphQlSupport/GraphQlSyncable plumbing to generated query-Things and mutation-Actions. Without this, generated operations would have shapes but no wire documents to send. It consumes the QueryParser operation models.

## How it fits

Runs over parsed operations inside generation; GraphQlSource consumes the emitted support at runtime. Enablement flows from Config.enableGraphQl.

## Key pieces

- `operation/graphQl` — per-operation document and variable emission
- `addGraphQlSupport` — attaching the transport-facing support interface to generated operations

## Junior notes

- Operation text comes from queries.graphql, never hand-written in generators: fix the .graphql when documents are wrong.
