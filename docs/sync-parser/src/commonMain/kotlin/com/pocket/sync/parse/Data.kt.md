# sync-parser/src/commonMain/kotlin/com/pocket/sync/parse/Data.kt

## What this is

The raw, unresolved schema model: FigmentsData plus per-definition data holders (ThingData, SyncableData, DefinitionData, PropertyData, DefinitionProperties/SyncableProperties) capturing schema files exactly as written, references still as names, nothing validated yet. The kdoc jokes that Figments exist only in the parser's imagination (memory). This deliberately dumb layer lets syntax extraction succeed independently of semantic validation.

## How it fits

SpecParser/QueryParser produce these from GraphQL files (step one of Parser's two-phase design); Validate.resolve then turns them into the typed Definition model. Codegen never sees this layer, only the resolved one.

## Key pieces

- `FigmentsData` — the whole raw schema: every definition still carrying unresolved name references
- `ThingData/SyncableData/DefinitionData` — per-definition raw holders for things, queries/mutations, and shared definition data
- `PropertyData/DefinitionProperties/SyncableProperties` — raw field/property bags captured verbatim from the schema text

## Junior notes

- References here are just strings: any code needing actual linked definitions must go through the resolved Definition model instead.
