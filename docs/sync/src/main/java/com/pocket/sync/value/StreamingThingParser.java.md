# sync/src/main/java/com/pocket/sync/value/StreamingThingParser.java

## What this is

One branch of the parser family: creating Things incrementally from a token stream (Jackson streaming) instead of a materialized JSON tree. Streaming matters for large payloads (long lists, discovery feeds) where building a full in-memory tree first would spike memory. It complements StreamingTypeParser (scalar/collection values) and the tree-based SyncableParser/TypeParser.

## How it fits

Generated Thing parsers implement this alongside the tree variants; RemapTest parses the same fixtures through both paths to prove they agree. Transports pick the branch matching how they buffered the response.

## Key pieces

- `streaming create methods` — token-by-token Thing construction without an intermediate JSON tree

## Junior notes

- Both parser branches must stay behaviorally identical: a fix applied to only one silently forks parsing by payload size.
