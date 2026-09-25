# sync-gen/src/main/java/com/pocket/sync/print/java/ActionGenerator.java

## What this is

Emits one immutable Action class per schema action: typed fields for inputs, builder, JSON/bundle/binary serialization, and the ActionResolved wiring for reply parsing (note the overloaded ActionResolved creator helpers plus resolver in the method list). It is the action half of SyncableGenerator's shared logic, specialized to intents rather than state.

## How it fits

Runs per action inside Generator.generate(); the emitted classes are what app code builds and passes to a Source. ActionExample/NoEffectAction/ResolvedAction in the examples show its output range.

## Key pieces

- `per-action class emission` — fields, builders, serializers, and resolved-reply support for one action
- `ActionResolved creators/resolver` — generating the reply-parsing half of actions that return data

## Junior notes

- Actions are intents with builders: if generated code lacks a setter you need, the schema input is missing, not the generator.
