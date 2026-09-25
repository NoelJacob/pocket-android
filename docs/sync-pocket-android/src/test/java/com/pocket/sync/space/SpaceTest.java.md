# sync-pocket-android/src/test/java/com/pocket/sync/space/SpaceTest.java

## What this is

The Space contract suite (~956 lines, the largest engine test): remember/forget lifetimes (forgetHolder/forgetOne/forgetMultiple/remembering/rememberWithMultiples), update visibility (update, updateDoesNotEffectNonRemembered, get/rememberDoesNotEffectState), nested persistence (saveComplexNestedWithReferences, canGetNestedThingFromSavingItsParent, diffIncludesEffectedReferences), reactive recomputation (reactiveToType/reactiveToTypeField/reactiveToField/reactiveToSiblings), and deep nesting (collectionsNest via DeepCollectionBuilder). MutableSpaceTest reruns it all against production storage.

## How it fits

The executable definition of correct Space behavior; Space/Bindable/Holder/Reactions javadocs describe in prose what this proves in code.

## Key pieces

- `remember/forget lifetime tests` — holder-scoped retention and release semantics
- `update visibility tests` — who sees updates and whose reads stay side-effect-free
- `nested-reference tests` — graphs entering as one payload staying addressable as many Things
- `reactiveTo* tests` — derive triggering precisely on watched-type, field, and sibling changes
- `getDoesNotEffectState / rememberDoesNotEffectState / rememberWithMultiples / updateDoesNotEffectNonRemembered / update / forgetHolder / forgetOne / forgetMultiple` — further cases in this file covering adjacent behavior of the same contract

## Junior notes

- Reads must not mutate: the DoesNotEffectState tests pin that gets and remembers leave stored state untouched.
