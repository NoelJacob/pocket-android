# sync-gen/src/main/java/com/pocket/sync/print/java/ThingGenerator.java

## What this is

Emits one immutable Thing class per schema thing (setup orchestrates; mutable/deriveClass/builderClass emit the MutableThing support, derive dispatch, and builder; redact emits sensitive-field handling), delegating compression to CompressGenerator. At ~1022 lines it is the largest emitter because Things carry every concern: fields, builders, parsers, serializers, mutable mirrors, derive hooks, and redaction. Navigate by emitted concern, not linearly.

## How it fits

Runs per thing inside Generator.generate(); ThingExample and friends show its output. SyncableGenerator supplies the shared half-logic.

## Key pieces

- `setup` — per-thing orchestration across all emitted concerns
- `mutable/builderClass` — MutableSpace support plus the fluent builder emission
- `deriveClass` — derive-dispatch hooks connecting Things to the Reactions pipeline
- `redact` — dangerous-field handling in serialization paths

## Junior notes

- The largest emitter concentrates the most compat risk: binary order, JSON names, and builder shape all freeze into client behavior.
