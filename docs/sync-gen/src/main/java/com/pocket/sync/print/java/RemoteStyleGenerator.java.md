# sync-gen/src/main/java/com/pocket/sync/print/java/RemoteStyleGenerator.java

## What this is

Emits the RemoteStyle enum from schema endpoint declarations: one constant per endpoint family (classname/enumvalue name them, getTypeSpec assembles the JavaPoet type), mirroring AuthTypeGenerator's shape for auth. Generated operations carry their style so JsonConfig aliasing and transports handle each endpoint's naming correctly.

## How it fits

Runs inside Generator.generate(); transports and JsonConfig consume the emitted enum at runtime. New endpoint families arrive via schema, not edits.

## Key pieces

- `classname/enumvalue/getTypeSpec` — enum naming, per-endpoint constants, and type assembly

## Junior notes

- Endpoint naming conventions change per server family: keep style-specific quirks in generated mappings, not in transport code.
