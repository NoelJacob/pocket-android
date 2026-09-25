# sync-parser/src/commonMain/kotlin/com/pocket/sync/type/Type.kt

## What this is

The field-type vocabulary: FieldType is anything a field can hold, with CollectionType (ListType, MapType over an inner AllowedInCollectionType) for containers, DefinitionType/ReferenceType for links to schema definitions, and OpenType for fields accepting many Thing types (InterfaceType and VarietyType implement it via compatible()). StatefulDefinition marks definitions that hold state (Value, Thing, Enum). Codegen branches on these constantly through WhenType.

## How it fits

Every field in every resolved definition carries one of these; generators (ThingGenerator, OpenTypes, ModellerGenerator) switch on the kind to emit correct Java types, parsers, and creators. compatible() answers which Things an open field may hold.

## Key pieces

- `FieldType/CollectionType/AllowedInCollectionType` — the kind lattice: scalars, containers, and what may nest inside them
- `ReferenceType/DefinitionType` — typed links from fields to their schema definitions
- `OpenType (InterfaceType/VarietyType)` — multi-Thing fields with compatible() enumerating the allowed set
- `ListType/MapType with inner` — container shapes parameterized by their element type

## Junior notes

- Open versus closed is the key distinction: open fields need discriminator dispatch at parse time (see OpenParser), closed ones parse directly.
