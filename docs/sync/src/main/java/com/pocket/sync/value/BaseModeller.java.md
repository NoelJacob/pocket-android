# sync/src/main/java/com/pocket/sync/value/BaseModeller.java

## What this is

Hand-written counterpart to the generated Modeller: value-shaping helpers (asList/asMap overloads, remap for key translation, toJsonValue/toObjectNode for JSON output) that are easier to maintain by hand than to generate. Generated Modeller classes (see ModellerGenerator and Pocket's generated Modeller) build on these for the per-type specifics. Keeping shared shaping here avoids emitting the same utility code into every generated file.

## How it fits

Generated parsers and serializers call these when converting between JSON nodes, Java collections, and modeled values. RemapTest exercises remap through both JSON and streaming parser paths.

## Key pieces

- `asList/asMap overloads` — normalize the many JSON shapes of collections into usable Java lists and maps
- `remap` — translate between keyed-object and list wire shapes for the same logical collection
- `toJsonValue/toObjectNode` — emit modeled values back to JSON nodes

## Junior notes

- Fix shared shaping here, per-type quirks in the generated Modeller: putting one-off hacks here infects every type.
