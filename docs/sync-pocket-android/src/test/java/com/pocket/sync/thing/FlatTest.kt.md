# sync-pocket-android/src/test/java/com/pocket/sync/thing/FlatTest.kt

## What this is

Traversal proofs for Thing.flat: interface-of-ids, lists of interfaces, mixed id/non-id implementations, and deep mixed nesting cases assert flattening collects exactly the identifiable descendants (built via InterfaceAllIdentifiable/OpenUsages builders, checked with assertFlattened). Flat is how the engine splits one incoming graph into separately storable Things.

## How it fits

Guards FlatUtils.flatten across every nesting shape; SubthingsTest covers the companion subthings traversal.

## Key pieces

- `interface-of-ids cases` — single and listed interface children collected by identity
- `mixed id/non-id cases` — only the identifiable members collected from heterogeneous nesting
- `interface of ids / list of interface of ids / id implementation within interface with a mixture of ids and non ids / non id implementation within interface with a mixture of ids and non ids / id implementation within a list of interface with a mixture of ids and non ids` — further cases in this file covering adjacent behavior of the same contract

## Junior notes

- Flattening follows identity: if a case collects too much or too little, check isIdentifiable answers before the traversal.
