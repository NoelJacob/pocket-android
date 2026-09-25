# sync/src/main/java/com/pocket/sync/value/TypeParser.java

## What this is

The tree-based parser contract for individual values: building field types from JSON nodes, with Allow flags gating what is acceptable in the result. It pairs with SyncableParser (whole Things/Actions) the way StreamingTypeParser pairs with StreamingThingParser. Generated Modeller code implements these per value type.

## How it fits

Every fromJson path funnels through here; OpenParser handles the open-type fan-out within it. Keep tree and streaming value parsing behaviorally identical.

## Key pieces

- `create(JsonNode, Allow...)` — permission-gated construction of one value from its JSON form

## Junior notes

- New scalar kinds need both a TypeParser and a StreamingTypeParser implementation, or one input shape will fail.
