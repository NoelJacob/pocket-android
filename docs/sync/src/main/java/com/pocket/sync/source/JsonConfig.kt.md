# sync/src/main/java/com/pocket/sync/source/JsonConfig.kt

## What this is

One object holding the JSON parsing rules for a source: which RemoteStyle aliases to read/write (server field names often differ from client names) and whether enums arrive as integers (the old V3 API encoded some enums like item status as numbers) or strings (GraphQL uses names). Every fromJson/toJson call threads this through so parsing stays consistent.

## How it fits

Sources hand this to generated parsers on every parse/serialize: PocketV3SourceTest and GraphQlSourceTest both construct sources with an explicit JsonConfig to prove each wire format parses. Syncable.NO_ALIASES is the no-remapping default used in tests.

## Key pieces

- `remote (RemoteStyle)` — which server naming convention applies, controlling field alias mapping
- `supportsIntEnums` — whether to accept integer-encoded enums for legacy V3-style payloads

## Junior notes

- Mismatched config is the first suspect when fields parse as null: check the RemoteStyle and int-enum flag before blaming the server payload.
