# sync-gen/src/main/java/com/pocket/sync/print/java/ActionsSpecGenerator.java

## What this is

Emits the Spec.actions() implementation class: the registry object that makes every action of the API available to the Spec at runtime. classname() names it; the generated file's own javadoc documents how the Spec plugs it in. Without this, the Spec would know action logic but have no enumerable set of actions to offer sources.

## How it fits

Runs inside Generator.generate(); the concrete Spec ( Pocket's real spec, SyncTestsSpec) returns the emitted instance from actions(). Pairs with ThingsSpecGenerator on the state half.

## Key pieces

- `classname` — derives the emitted actions-registry class name

## Junior notes

- Registries are generated so they cannot drift from the schema: never hand-maintain a parallel action list.
