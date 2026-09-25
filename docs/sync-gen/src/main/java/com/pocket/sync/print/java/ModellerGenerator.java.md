# sync-gen/src/main/java/com/pocket/sync/print/java/ModellerGenerator.java

## What this is

Emits the per-API Modeller class: static helpers other generated classes call to work with Value types in Java (creatorName/streamingCreatorName/byteCreatorName name the per-type creator methods across JSON, streaming, and binary inputs). The Modeller is the shared value-handling hub so Things and Actions do not each inline parsing logic. BaseModeller supplies the hand-written half.

## How it fits

Runs inside Generator.generate(); generated parsers/serializers statically reference the emitted methods. ValueModeller/StandardModeller define per-type modeling rules it follows.

## Key pieces

- `creatorName/streamingCreatorName/byteCreatorName` — per-type creator naming across the three input shapes

## Junior notes

- Value handling lives here so fixes apply once: a parsing quirk patched into individual Things instead would need regeneration-wide repetition.
