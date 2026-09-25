# sync/src/main/java/com/pocket/sync/source/protocol/graphql/GraphQlSupport.kt

## What this is

The per-operation helper a GraphQlSyncable carries so GraphQlSource can do its job: the GraphQL document, variables, and response parsing for that one query or mutation. Think of GraphQlSource as the post office and this as the correctly addressed envelope each operation provides. Generated code implements it from the queries.graphql operations.

## How it fits

Generated Thing (query) and Action (mutation) classes expose this, and the source requires it before it will send anything. GraphQlGenerator writes the boilerplate; schema authors only write the .graphql operation text.

## Key pieces

- `operation document/variables support` — what to send for this specific query or mutation
- `response parsing support` — how to turn this operation's data payload back into typed Things

## Junior notes

- If the source rejects an operation as unsupported, it is almost always missing this support rather than a network problem.
