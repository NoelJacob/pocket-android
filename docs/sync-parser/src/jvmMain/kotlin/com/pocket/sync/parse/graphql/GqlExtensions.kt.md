# sync-parser/src/jvmMain/kotlin/com/pocket/sync/parse/graphql/GqlExtensions.kt

## What this is

Tiny conveniences over the GraphQL-Java AST: most nodes carry names, so these helpers return a sane default when one is missing, plus list helpers smoothing over nullable AST collections. They exist to keep SpecParser and QueryParser readable: schema parsing touches thousands of AST nodes, and null-guarding each by hand would bury the logic.

## How it fits

Used only inside the jvmMain GraphQL parsing step (SpecParser/QueryParser); the commonMain model never sees AST types. JVM-only because GraphQL-Java is a JVM library, which is why this lives in jvmMain.

## Key pieces

- `name-default helpers` — null-safe name access with defaults for unnamed AST nodes
- `list helpers` — nullable-collection smoothing for AST child lists

## Junior notes

- AST helpers must stay total (never throw on odd nodes): a crashing helper would abort a whole schema parse on one weird node.
