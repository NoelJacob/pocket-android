# sync-parser/src/commonMain/kotlin/com/pocket/sync/print/figment/FigmentPrinter.kt

## What this is

Serializes resolved definitions back to figment/schema text: DefinitionType/body/List/toFigment renderers plus Spacer/SpacerData formatting helpers turn the in-memory model back into readable, diffable schema syntax. Round-tripping matters for debugging (print what the parser understood) and for tooling that normalizes or migrates schemas. Equality and hashCode support test comparisons of printed output.

## How it fits

Parser tests and schema-migration tooling print through here; authors use the output to verify the parser understood their schema as intended. It is the inverse of SpecParser/QueryParser.

## Key pieces

- `toFigment/body renderers` — converting resolved definitions and their bodies back to schema text
- `Spacer/SpacerData` — indentation and layout helpers keeping printed output stable and diffable

## Junior notes

- Printed output is canonical, not verbatim: formatting and ordering normalize, so diff printed-against-printed, not against hand-written source.
