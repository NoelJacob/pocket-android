# sync-pocket-android/src/test/java/com/pocket/sync/thing/SubthingsTest.kt

## What this is

Traversal proofs for Thing.subthings: the companion to FlatTest, asserting the direct-child (rather than transitive) nested-Thing enumeration across the same interface/list/map/mixed shapes. Flat answers everything-below; subthings answers one-level-down, and both must agree with the identity model.

## How it fits

Guards FlatUtils reference enumeration; run both files together after any nesting or identity change.

## Key pieces

- `subthings cases` — direct-child enumeration across interface, collection, and mixed nesting
- `interface of ids only / list of interface of ids only / id implementation within interface with a mixture of ids and non ids / non id implementation within interface with a mixture of ids and non ids / id implementation within a list of interface with a mixture of ids and non ids` — further cases in this file covering adjacent behavior of the same contract

## Junior notes

- Flat versus subthings confusion causes double-processing or missed children: pick by depth needed, not habit.
