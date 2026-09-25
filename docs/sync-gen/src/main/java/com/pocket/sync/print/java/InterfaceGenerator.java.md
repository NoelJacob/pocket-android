# sync-gen/src/main/java/com/pocket/sync/print/java/InterfaceGenerator.java

## What this is

Emits one Java interface per figment interface: the contract type plus per-field accessor methods (create builds the interface, fieldMethod emits each accessor) that every implementing Thing honors. Interfaces model open-ended shared contracts (anything with an id and title), distinct from varieties (closed unions). OpenParser dispatches these at runtime via _type tags.

## How it fits

Runs per interface inside Generator.generate(); InterfaceExample plus the Interface*Impl fixtures show the emitted shape and its implementations.

## Key pieces

- `create/fieldMethod` — interface emission plus per-field accessor generation

## Junior notes

- Prefer interfaces for open-ended contracts and varieties for closed sets: the wrong choice either blocks new types or forfeits exhaustiveness.
