# sync-gen/src/main/java/com/pocket/sync/print/java/ThingsSpecGenerator.java

## What this is

Emits the Spec.things() implementation class: the runtime registry of every Thing the API offers (classname names it; the generated file's javadoc documents Spec integration). It mirrors ActionsSpecGenerator on the state half: without it the Spec would know thing logic but offer no enumerable set for sources, persistence, and tooling to work with.

## How it fits

Runs inside Generator.generate(); concrete specs return the emitted instance from things(). Registries stay generated so schema and runtime never drift.

## Key pieces

- `classname` — derives the emitted things-registry class name

## Junior notes

- Same rule as actions: never hand-maintain a parallel thing list; the generated registry is the source of truth.
