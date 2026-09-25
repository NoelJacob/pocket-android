# sync-parser/src/commonMain/kotlin/com/pocket/sync/type/Definition.kt

## What this is

The resolved schema type system: concrete, immutable, fully-validated classes for every schema concept (Thing, Action, Value, Enum, Remote, Auth, Variety, interfaces, Syncable operation bases), all extending Property/Definition roots with DefinitionBase and SyncableBase sharing behavior. Helpers like interfacesRecursive, isIdentifiable, rootValue, hasIntegerValues, and ids answer the structural questions codegen asks constantly. Everything resolves and validates in constructors, so an instance in hand is always trustworthy. This 860-line file is the heart of the parser: read it by definition family, not top to bottom.

## How it fits

Validate.resolve produces these; Figments queries them; every sync-gen generator consumes them. If a schema concept exists, its resolved form lives here.

## Key pieces

- `Thing/Action/Value/Enum/Remote/Auth/Variety` — the resolved per-concept definitions carrying all validated details
- `Syncable/SyncableBase (queries/mutations)` — operation definitions binding GraphQL operations to Things/Actions
- `Interface/Synthetic` — shared-contract definitions plus compiler-derived synthetic ones
- `interfacesRecursive/isIdentifiable/rootValue/hasIntegerValues/ids` — structural queries driving codegen branching

## Junior notes

- Over 400 lines by necessity: navigate by family (find your concept's class) rather than reading linearly.
- Instances are immutable and pre-validated: codegen can trust them without re-checking, which is the whole point of resolving in constructors.
