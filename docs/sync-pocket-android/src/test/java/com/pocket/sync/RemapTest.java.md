# sync-pocket-android/src/test/java/com/pocket/sync/RemapTest.java

## What this is

Remap (keyed-object versus list wire shapes) proofs: remapsObjectsAsList/remapsArrayAsList plus the FromParser streaming variants prove the same logical collection parses identically whether the server sends an object map or an array. Servers change envelope shapes across versions, and remap is what keeps both readable. remapper-array.json and remapper-obj.json are the paired fixtures.

## How it fits

Runs through BaseModeller.remap on both parser branches; a branch-specific failure means the tree/streaming mirror slipped.

## Key pieces

- `remapsObjectsAsList / remapsArrayAsList` — both envelope shapes yielding the same collection via tree parsing
- `remapsObjectsAsListFromParser / remapsArrayAsListFromParser` — the same proof through the streaming parser

## Junior notes

- Paired fixtures must stay logically identical: if you edit one remapper JSON, mirror the change in the other.
