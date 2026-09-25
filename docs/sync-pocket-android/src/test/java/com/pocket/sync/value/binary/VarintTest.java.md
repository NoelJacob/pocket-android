# sync-pocket-android/src/test/java/com/pocket/sync/value/binary/VarintTest.java

## What this is

Bit-level proofs for Varint packing: varint/varlong cases assert write-then-read round trips across the int and long ranges, including boundary magnitudes where byte width changes. Varint underpins every number in the binary persistence format, so an off-by-one here would corrupt stored Spaces silently.

## How it fits

Guards Varint; run on any binary-format change alongside CompressionTest.

## Key pieces

- `varint / varlong` — round trips spanning small values through width-change boundaries to extremes

## Junior notes

- Always test the width-change boundaries (127/128, 16383/16384, ...): packing bugs live exactly there.
