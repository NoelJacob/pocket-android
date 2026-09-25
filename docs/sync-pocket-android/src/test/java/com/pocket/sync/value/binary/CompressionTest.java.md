# sync-pocket-android/src/test/java/com/pocket/sync/value/binary/CompressionTest.java

## What this is

Binary round-trip proof over real payloads: compresses feed, notifications, profile-feed Things plus a Features flag set through ByteWriter, restores via ByteReader creators, and asserts STATE equality. Real shaped data (not minimal fixtures) is deliberate: field-order and encoding bugs only show up at realistic breadth. It guards everything SqliteBinaryStorage persists.

## How it fits

Runs ThingMock fixtures through CompressGenerator output; any compress/uncompress emitter change must keep this green.

## Key pieces

- `compression` — write-then-read round trip asserting state equality across four representative Things

## Junior notes

- Round-trip equality is STATE, not identity: same data restored is success even though instances differ.
