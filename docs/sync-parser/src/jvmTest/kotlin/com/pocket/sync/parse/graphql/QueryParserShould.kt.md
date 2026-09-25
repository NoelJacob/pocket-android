# sync-parser/src/jvmTest/kotlin/com/pocket/sync/parse/graphql/QueryParserShould.kt

## What this is

Unit tests for QueryParser: setup builds a parser over fixture operations and the test methods assert operations, fragments, and variables parse into the expected figment models. As the only jvmTest in the parser, it guards the operations half of ingestion (schemas get coverage indirectly through codegen tests). If operation codegen ever emits nonsense, these tests say whether the parser or the generator misunderstood the .graphql.

## How it fits

Guards QueryParser; run it when touching operation parsing, fragment handling, or variable modeling.

## Key pieces

- `setup` — fixture-backed parser construction shared by the test methods

## Junior notes

- Parser tests assert on the in-memory model, not generated text: generation regressions belong to sync-gen test coverage.
