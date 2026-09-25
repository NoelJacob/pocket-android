# sync/src/main/java/com/pocket/sync/source/Specd.java

## What this is

A tiny accessor interface for anything that can hand you its Spec: the codegen'd description of which Things and Actions exist and how Actions change Things. Generic engine code uses this to ask a source what it is capable of without knowing the concrete source class. If you hold a Specd, you can validate, route, and apply work correctly.

## How it fits

Sources implement this so helpers (routing, persistence, tests) can fetch the Spec for any source uniformly. InMemorySource.spec() is the simplest example.

## Key pieces

- `Spec accessor` — the one method that exposes the source's domain definition for generic handling

## Junior notes

- When generic code needs to branch on capability, ask the Spec here rather than instanceof-checking concrete source classes.
