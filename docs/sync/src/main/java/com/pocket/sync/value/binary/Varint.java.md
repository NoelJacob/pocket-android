# sync/src/main/java/com/pocket/sync/value/binary/Varint.java

## What this is

Minimal-byte integer packing adapted from Protocol Buffers: small numbers occupy few bytes, large ones expand as needed, via writeInt/writeLong and readInt/readLong. ByteWriter/ByteReader use it under the hood so persisted Spaces stay small on disk. The javadoc notes where the logic follows protobuf exactly and where it deliberately differs.

## How it fits

Every compressed Thing benefits transparently; VarintTest pins bit-level round trips for both int and long ranges. No call site uses this directly except the binary format internals.

## Key pieces

- `writeInt/writeLong` — size-proportional integer encoding into the byte stream
- `readInt/readLong` — the exact-inverse decoders

## Junior notes

- Varint encodes magnitude, not semantics: signedness and zig-zag choices are fixed by these helpers, so never hand-roll adjacent packing.
