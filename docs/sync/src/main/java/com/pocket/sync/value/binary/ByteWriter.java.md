# sync/src/main/java/com/pocket/sync/value/binary/ByteWriter.java

## What this is

The write half of the compact binary persistence format: an append-only byte sink with typed writes (writeString/writeInt/writeLong/writeDouble/writeBoolean, bit-level writeBit/finishByte) producing a byte array via readByteArray, plus a sha256 helper for integrity checks. Thing.compress implementations serialize field-by-field into this; ByteReader reverses the process on restore. Compactness matters because whole Spaces get snapshotted to SQLite.

## How it fits

Generated compress() methods write through this into SqliteBinaryStorage blobs; CompressionTest proves the round trip. Varint keeps small numbers to a few bytes underneath.

## Key pieces

- `typed writes` — appending each field value in a fixed binary encoding
- `writeBit/finishByte` — bit-packing for boolean-dense state
- `readByteArray/sha256` — extracting the finished blob plus an integrity digest

## Junior notes

- Appending new fields is safe, reordering or retyping old ones is not: old blobs on user devices must still read after an upgrade.
