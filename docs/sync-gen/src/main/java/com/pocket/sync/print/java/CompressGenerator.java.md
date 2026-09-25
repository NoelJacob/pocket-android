# sync-gen/src/main/java/com/pocket/sync/print/java/CompressGenerator.java

## What this is

The compression half of Thing emission, split out of ThingGenerator for readability: setup plus compress/uncompress methods serializing each Thing to/from the ByteWriter/ByteReader binary format. Binary field order here must match the reader exactly; there are no field names on the wire. Keeping it separate means binary-format changes touch one file, not the whole Thing emitter.

## How it fits

Called by ThingGenerator.setup during per-thing emission; the output is what SqliteBinaryStorage persists and CompressionTest round-trips.

## Key pieces

- `setup/compress/uncompress` — per-thing binary serializer/deserializer emission

## Junior notes

- Binary order is a silent compat contract: append new fields, never reorder, or old blobs on user devices stop restoring.
