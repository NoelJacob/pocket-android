# sync/src/main/java/com/pocket/sync/value/SyncableParser.java

## What this is

The tree-based parser contract for whole syncables: building Things and Actions from a materialized JSON tree (Jackson JsonNode). It is the counterpart to TypeParser (individual values) on the tree side, and to StreamingThingParser on the streaming side. Generated classes implement this per type; AndroidParser-style platform hooks handle special scalars like HTML.

## How it fits

Transports and tests parse server JSON through these (ThingMock parses every mock fixture this way); JsonConfig and Allow flags tune each call. Prefer streaming variants only when payload size demands it.

## Key pieces

- `tree-based syncable creation` — full-Thing/Action construction from parsed JSON nodes

## Junior notes

- Materializing the whole tree costs memory proportional to payload size: fine for items, reconsider for hundred-item feeds.
