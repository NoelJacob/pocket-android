# sync/src/main/java/com/pocket/sync/value/StreamingTypeParser.java

## What this is

The scalar/collection branch of the streaming parser family: creating values and field types token-by-token from a Jackson stream. It pairs with StreamingThingParser (whole Things) the way TypeParser pairs with SyncableParser on the tree side. Generated Modeller/parser code implements these per type so big responses parse with flat memory.

## How it fits

Same dual-path story as its Thing sibling: RemapTest runs fixtures through both streaming and tree parsers. Keep fixes mirrored.

## Key pieces

- `streaming value creation` — incremental parsing of scalars, lists, maps, and nested values

## Junior notes

- See StreamingThingParser notes: symmetric fixes across tree and streaming branches, always.
