# sync/src/main/java/com/pocket/sync/thing/Thing.java

## What this is

The state half of the engine: a Thing is an immutable snapshot of one server-side object (a saved item, a tag, a slate) with a stable identity (idkey), typed fields, equality modes (the nested Equality type distinguishes same-identity from same-state comparisons), and conversions to JSON, maps, and compressed bytes. Immutability lets the Space share instances freely and lets subscribers compare versions cheaply to decide what changed.

## How it fits

Spaces store them, Sources sync them, subscribers receive new versions of them, and generated subclasses (per schema type) supply the fields; ThingBuilder constructs them and FlatUtils splits their graphs. End to end, saving a URL walks through this file like this: the app builds a generated save Action (inputs: the URL) and hands it to the source, which applies it to the local Space first via the Spec so the UI updates instantly (this optimistic local apply is why saves feel immediate), then queues the Action for the remote transport; the server reply is parsed back into Things that are imprinted into the Space through the Resolver, and every subscriber watching those Things receives a Changes update and re-renders.

## Key pieces

- `identity (idkey)` — the stable key the Space, Resolver, and subscriptions all key off
- `Equality` — identity-versus-state comparison modes so updates can be detected without deep field walks
- `toJson/toMap/compress` — the three serializations: wire JSON, generic maps, and compact bytes for disk
- `reactions/flat/subthings` — reactive dependency reporting plus nested-Thing traversal entry points

## Junior notes

- Identity versus state is the core distinction: two Things can share identity with different state (an update), and merging must preserve identity while replacing state.
