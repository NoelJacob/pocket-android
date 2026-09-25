# sync/src/main/java/com/pocket/sync/value/OpenParser.java

## What this is

The helper for parsing open types: fields whose value could be any of several Thing types (interfaces, where many types implement one contract, or varieties, the schema's union of a fixed set). The payload carries a discriminator (usually a _type tag) plus a spec-declared set of possibilities, and this class implements the fan-out that picks the right concrete parser. Its create overloads cover the JSON and streaming entry points.

## How it fits

Generated code delegates interface/variety fields here instead of inlining the dispatch per field; OpenTypeTest and OpenDangerousUsages fixtures prove mixed payloads resolve correctly. See the Figment docs on open types for the full rules.

## Key pieces

- `create overloads` — discriminator-based dispatch to the matching concrete parser across input shapes

## Junior notes

- Unknown _type tags at parse time mean schema skew (server added a type this build predates): fail visibly rather than guessing.
