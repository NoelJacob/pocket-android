# sync-gen/src/main/java/com/pocket/sync/print/java/EnumGenerator.java

## What this is

Emits one enum class per schema enum: constants with wire values (valueFieldName), string- versus integer-backed bases (StringEnum/IntegerEnum), and collection-safe handling. Enums serialize by wire value, so emitted constants preserve the schema's serialized form even when Java names differ. BasicEnum/IntegerEnum in the examples show both encodings.

## How it fits

Runs per enum inside Generator.generate(); parsers, serializers, and usage tracking all key off the emitted wire values.

## Key pieces

- `valueFieldName` — the wire-value backing field keeping serialized forms stable
- `string/integer backing` — selecting the EnumType base matching the schema's encoding

## Junior notes

- Wire values are a compat surface: renaming a serialized value breaks restores and usage history, so alias instead of rename.
