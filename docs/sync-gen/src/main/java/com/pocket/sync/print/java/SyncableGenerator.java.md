# sync-gen/src/main/java/com/pocket/sync/print/java/SyncableGenerator.java

## What this is

Shared emission for Things and Actions (both are Syncables): builders (addBuilder), constructors (addConstructor), fields (addFields with fieldJavaDoc), declared-value lists (addDeclared), JSON in/out (addFromJson/addFromParser/addToJson/addToString), creator wiring (addCreator), remote info (addRemoteInfo), and map forms (addToMap). ThingGenerator and ActionGenerator supply the half-specific parts; everything identical lives here once. At ~636 lines, read by method group.

## How it fits

Parent emitter for the two halves; fixes to shared serialization or builder shape land here and reach both Things and Actions.

## Key pieces

- `addBuilder/addConstructor/addFields` — construction and state emission shared by both halves
- `addFromJson/addFromParser/addToJson/addToString/addToMap` — the serialization suite emitted per type
- `addCreator/addRemoteInfo` — parser-creator linkage plus endpoint metadata emission

## Junior notes

- Shared means twice-affected: test both a Thing and an Action after any change here (EqualityTest plus action specs).
