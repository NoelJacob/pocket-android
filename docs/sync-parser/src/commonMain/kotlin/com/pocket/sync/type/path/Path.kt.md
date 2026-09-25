# sync-parser/src/commonMain/kotlin/com/pocket/sync/type/path/Path.kt

## What this is

Raw figment path syntax: parsing and representing paths like thing.field[index].key that address a field, key, or index deep inside a definition graph (field/key/index builders plus from() parsing and path/toString rendering, with PathSegment/PathSegmentType typing each step). This holds only the string structure; Reference is the validated, resolved counterpart. Paths appear in derives, reactions, and endpoint mappings wherever one field points at another.

## How it fits

Schema authors write these; the parser captures them here first, then Reference resolves them against actual definitions. See the figment spec's path section for the full grammar.

## Key pieces

- `from/path/toString` — parsing path text and rendering it back canonically
- `field/key/index` — building path steps addressing fields, map keys, and list positions
- `PathSegment/PathSegmentType` — the typed step representation

## Junior notes

- A path that parses is not necessarily valid: existence and type checks happen at Reference resolution, so path errors surface one phase later.
