# sync/src/main/java/com/pocket/sync/source/protocol/graphql/GraphQlSyncable.kt

## What this is

The marker that makes a Thing or Action sendable over GraphQL: queries are Things (they fetch state) and mutations are Actions (they change state), and this interface tags a generated class as carrying its GraphQlSupport envelope. GraphQlSource refuses anything without it, which keeps non-GraphQL operations from accidentally going down the GraphQL path.

## How it fits

Generated query-Things and mutation-Actions implement this; transports check for it when routing work to the GraphQL endpoint versus legacy remotes. The test schema's GraphQlQueryReturns* fixtures are minimal examples.

## Key pieces

- `Thing-as-query / Action-as-mutation tag` — the compile-time proof an operation belongs on the GraphQL wire

## Junior notes

- New schema operations must flow through codegen to gain this marker; hand-written Things cannot be GraphQL-sent.
