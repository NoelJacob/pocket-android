# sync-gen/src/main/java/com/pocket/sync/print/java/ClassGenerator.java

## What this is

The shared base for all single-class emitters: holds the JavaPoet plumbing (getTypeBuilder/getTypeSpec) every generator uses to assemble a Java class programmatically. JavaPoet is the library for building Java source as data structures instead of string concatenation. Subclasses fill in fields and methods; this handles the class skeleton, imports, and naming consistently.

## How it fits

Every *Generator extends this (directly or via SyncableGenerator); cross-cutting emission changes (headers, annotations, style) land here once.

## Key pieces

- `getTypeBuilder/getTypeSpec` — the JavaPoet class-assembly entry points all generators share

## Junior notes

- Put shared emission mechanics here, per-concept logic in subclasses: a formatting fix should never require touching twenty generators.
