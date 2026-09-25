# sync-parser/src/commonMain/kotlin/com/pocket/sync/parse/Parser.kt

## What this is

The two-phase parsing contract: phase one extracts raw FigmentsData/PropertyData exactly as the schema files describe (syntax rules only), phase two resolves cross-definition references and fully validates, producing the typed Definition graph. Splitting the phases means syntax errors report cleanly without half-resolved state, and resolvers never see malformed input. The parse overloads cover the schema-file entry points.

## How it fits

SpecParser (schemas) and QueryParser (operations) implement this; Validate provides phase two, and sync-gen consumes only phase-two output. New schema syntax lands in phase one, new semantic rules in phase two.

## Key pieces

- `parse overloads` — entry points turning schema/operation files into raw figment data
- `two-phase split` — syntax extraction first, reference resolution plus validation second

## Junior notes

- Validate against the resolved model, never the raw data: phase-one output may reference definitions that do not exist.
