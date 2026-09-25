# sync-parser/src/commonMain/kotlin/com/pocket/sync/parse/Validate.kt

## What this is

Phase two of parsing: takes raw FigmentsData, fully validates it, and returns resolved immutable instances with every reference linked. The Stage/Resolver/Referencer/ResolverImpl machinery tracks resolution order (with checkStage guards and OnlyAfterResolving markers on premature access), unknown() reports dangling references, and per-kind resolvers (definition/thing/action/value/enum) enforce each definition family's rules. ResolvingException is the typed failure.

## How it fits

Parser implementations call resolve to finish; Figments wraps the result for codegen. Schema authoring mistakes surface here as resolution errors rather than mysterious codegen output.

## Key pieces

- `resolve` — the validation-plus-linking pass producing the finished Definition graph
- `Stage/checkStage/OnlyAfterResolving` — ordering guards preventing access to references before they are linked
- `unknown/ResolvingException` — dangling-reference detection and its typed error
- `per-kind resolvers` — thing/action/value/enum-specific validation rules

## Junior notes

- Resolution errors are schema bugs, not parser bugs: read the unknown-reference message and fix the schema spelling first.
