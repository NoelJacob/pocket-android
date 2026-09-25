# sync-android/src/main/java/com/pocket/sync/value/Parceller.java

## What this is

Bridges sync values into Android's Bundle/Parcel world: put/get overloads write Things, lists, and enums (including string enums via getStringEnum/getList) into Bundles for fragment arguments, saved state, and intents, and read them back with a SyncableParser creator. Parcels are Android's cross-component byte transport, and Bundles are the key-value carriers built on them; this is how a Thing crosses a fragment transaction or process-death restore intact.

## How it fits

Screens pass Things through fragment arguments with these (see the thingArg delegate in Parceller.kt), and saved-instance-state restores use the same path. It complements SqliteBinaryStorage (whole-Space disk snapshots) with per-value Android transport.

## Key pieces

- `put overloads` — typed writes of Things, enums, and lists into a Bundle
- `get/getList/getStringEnum` — typed reads back out using the caller's parser creator
- `getStringEnum` — string-enum-aware read honoring the wire-name mapping

## Junior notes

- Parcelable/argument data has size limits (~1MB transactions): pass identities and re-read from the Space for huge graphs instead of parcelling whole trees.
