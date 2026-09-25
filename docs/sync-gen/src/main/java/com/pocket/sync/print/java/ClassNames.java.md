# sync-gen/src/main/java/com/pocket/sync/print/java/ClassNames.java

## What this is

The single registry of every Java class codegen references: engine types, JavaPoet types, Android types, and generated-type name patterns in one place. When an engine class moves packages, updating this one file repoints all emission instead of hunting string literals across generators. It has no behavior, only canonical names.

## How it fits

Imported by every generator; the highest-leverage boring file in sync-gen. Check here first when generated imports look wrong.

## Key pieces

- `canonical class references` — the one true name for each type emitted code depends on

## Junior notes

- Never inline a fully-qualified engine class name in a generator: add it here so moves stay one-line fixes.
