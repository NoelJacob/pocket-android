# sync-gen/src/main/java/com/pocket/sync/print/java/pocket/AndroidClassGenerator.java

## What this is

Pocket's production codegen entry point: main() runs Generator with PocketConfig over Pocket's real schemas, emitting into the neighboring Android project's sources. This is the button that turns Pocket API schema edits into the generated Thing/Action/Spec classes the app compiles against. Schema change to app code is one run of this away.

## How it fits

Invoked by Pocket's build when schemas change; PocketConfig holds the API-specific rules. Unlike the examples/test generators, its output ships to users.

## Key pieces

- `main` — production generation run wiring Pocket's schemas to the Android sources

## Junior notes

- Production output ships: a bad generation run breaks the app build loudly, which is preferable to silently stale generated code.
