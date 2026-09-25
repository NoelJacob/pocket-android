# sync-parser/src/commonMain/kotlin/com/pocket/sync/type/path/Reference.kt

## What this is

Validated, resolved references to a definition or to a field/parameter/value inside one: ReferenceData carries the raw pieces, Flavor/Mode describe the reference kind, ReferenceSegment types each step, and FigmentsResolver links everything against the schema (equalsTarget/equals/hashCode compare by target, splitCollectionSearches/splitCollections normalize collection steps, toPath renders back). The kdoc's Type/field/.field table summarizes which combinations are legal.

## How it fits

Derives, reactions, remotes, and endpoint mappings resolve their paths through here; codegen consumes only resolved References. A dangling or mistyped reference fails here with a location, not later in generated code.

## Key pieces

- `ReferenceData/Flavor/Mode` — the raw reference plus its kind classification
- `FigmentsResolver` — the linking pass binding references to actual definitions
- `equalsTarget/splitCollectionSearches/toPath` — target-based comparison, collection normalization, and rendering

## Junior notes

- When generated derive code points at the wrong field, check the Reference resolution first: codegen faithfully emits whatever resolved here.
