# sync/src/main/java/com/pocket/sync/value/ByteTypeParser.java

## What this is

The binary-side creator contract: build one value from the next bytes in a ByteReader. It mirrors the JSON-side TypeParser family for the compressed on-disk format (see Thing.compress): where JSON parsing reads nodes, this reads raw bytes in field order. Generated classes implement it per type so restores do not need JSON at all.

## How it fits

SqliteBinaryStorage restores flow through these: bytes come out of the blob column, ByteReader walks them, and each type's creator rebuilds its values. CompressionTest proves JSON and binary paths agree.

## Key pieces

- `byte-creator contract` — the single build-from-next-bytes operation every storable type implements

## Junior notes

- Binary field order is a silent contract: adding fields means appending in a compatible spot, never reordering existing ones.
