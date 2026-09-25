# sync-pocket-android/src/test/java/com/pocket/sync/EqualityTest.java

## What this is

The equals/hashCode contract suite for generated types (~731 lines): identity versus state equality, hashcode differences, parent-state exclusion, lists, maps, non-identifiables, flat comparisons, declared-state variants, identity nested in Things, collections, and objects, plus action equality. The Space keys everything off identity while updates compare state, so this file pins the distinction the whole engine depends on.

## How it fits

Runs against the generated test API (SyncTestsSpec fixtures); ThingUtil is the production code under test. A failure here can corrupt Space lookups, so treat it as engine-critical.

## Key pieces

- `identity / state / state_declared` — the core three-way equality distinction
- `hashcodeDifferences` — hash consistency with the equality modes
- `parentStatesIgnored / flat` — which surrounding state counts and which does not
- `lists / maps / nonIdentifibles` — collection and embedded-object equality
- `identityWithAThing / complexIdentity* / nestedIdentity*` — identity nested at every depth and container
- `action` — equality for the Action half
- `identityWithNonIdThing / complexIdentityWithAThing / complexIdentityWithNonIdThing / nestedIdentityInCollection / nestedIdentityInObject` — further cases in this file covering adjacent behavior of the same contract

## Junior notes

- Identity-versus-state confusion is the classic sync bug (duplicate rows, lost updates): when in doubt, re-read these tests.
