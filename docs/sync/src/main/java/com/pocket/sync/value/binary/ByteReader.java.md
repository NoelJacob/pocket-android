# sync/src/main/java/com/pocket/sync/value/binary/ByteReader.java

## What this is

The read half of the compact binary format used for on-disk persistence: constructed over a byte array previously written by ByteWriter, each read call consumes the next bytes as one typed value (readString/readInt/readLong/readDouble/readBoolean, plus readList/readMap and bit-level readBit/finishByte). Load/finish framing keeps sequential reads aligned. It reverses Thing.compress for Space restores without ever touching JSON.

## How it fits

SqliteBinaryStorage.restore drives these over stored blobs; CompressionTest round-trips real Things (feed, notifications, profile feed) through write-then-read to prove fidelity. Varint packs the numbers underneath.

## Key pieces

- `readString/readInt/readLong/readDouble/readBoolean` — typed sequential reads consuming exactly their value's bytes
- `readList/readMap` — collection reads rebuilding nested structures in order
- `readBit/finishByte` — bit-packing support for boolean-dense fields
- `load` — buffer setup framing the read sequence

## Junior notes

- Read order must exactly mirror write order: there are no field names in the binary format, so a mismatch silently scrambles values.
