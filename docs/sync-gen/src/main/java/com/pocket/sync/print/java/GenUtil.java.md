# sync-gen/src/main/java/com/pocket/sync/print/java/GenUtil.java

## What this is

Shared Java-emission utilities: name sanitizers (toValidClassName/toValidMethodName/toValidFieldName/toValidConstantName), JavaPoet type mapping (toTypeName overloads), inner-class helpers (innerclass/createInnerClassName), and JSON-creator code snippets (creatorFromJsonCode/streamingCreatorFromJsonCode/addParamIfThing). At ~714 lines it is the toolbox every generator reaches for so GraphQL-ish names become legal, consistent Java. Read by helper, not linearly.

## How it fits

Used across all generators; a naming inconsistency in output usually traces to exactly one helper here. Keep helpers total: they run over arbitrary schema names.

## Key pieces

- `toValid*Name` — sanitizers turning schema names into legal Java class/method/field/constant names
- `toTypeName overloads` — schema-type to JavaPoet-type mapping
- `creatorFromJsonCode/streamingCreatorFromJsonCode` — code snippets wiring generated parsers to JSON and streaming inputs

## Junior notes

- Sanitizers must be deterministic and collision-free: two schema names mapping to one Java name would silently merge types.
