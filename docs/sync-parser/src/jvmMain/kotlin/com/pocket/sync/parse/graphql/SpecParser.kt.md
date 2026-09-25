# sync-parser/src/jvmMain/kotlin/com/pocket/sync/parse/graphql/SpecParser.kt

## What this is

Turns GraphQL schemas (.graphqls) into the figment models codegen understands: forFile/forNode walk schema files and AST nodes (scalars, types, mutations, directives like remote/endpoint/auth), baseAction wires action inheritance, and the Remotes helper collects endpoint declarations. The parse overloads are the pipeline entry points producing FigmentsData for validation. At ~790 lines it is the biggest parser file: navigate by directive/node kind, not linearly.

## How it fits

First step of the codegen pipeline (schema in, FigmentsData out); Validate.resolve finishes the job. All schema-syntax support (new scalars, directives, definition kinds) lands here.

## Key pieces

- `parse/forFile/forNode` — schema-file entry points plus per-file and per-node walkers
- `baseAction` — action-inheritance wiring from schema declarations
- `Remotes` — endpoint-declaration collection for remote/transport generation

## Junior notes

- New schema syntax needs three touches: parse it here, validate it in Validate, and emit it in the right sync-gen generator.
