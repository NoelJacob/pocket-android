# sync-parser/src/jvmMain/kotlin/com/pocket/sync/parse/graphql/QueryParser.kt

## What this is

Turns GraphQL operations files (queries and mutations) into figment definitions: it parses each operation, follows fragment spreads (findUsedFragments), and builds the in-memory operation model the engine uses to generate the matching query-Things and mutation-Actions (the parse overloads cover single and batch entry). This is how a queries.graphql operation becomes a class you can build and pass to sync().

## How it fits

Runs after SpecParser in the codegen pipeline: the schema defines what exists, this defines which operations the app actually uses. QueryParserShould pins its behavior; GraphQlGenerator later emits the operation support code.

## Key pieces

- `parse overloads` — operations-file entry points producing figment operation models
- `findUsedFragments` — fragment-dependency collection so generated operations include everything they spread

## Junior notes

- Every fragment an operation spreads must be reachable from the parsed files: a missing fragment fails here, not in generated code.
