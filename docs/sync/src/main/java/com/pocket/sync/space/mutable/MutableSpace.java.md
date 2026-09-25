# sync/src/main/java/com/pocket/sync/space/mutable/MutableSpace.java

## What this is

The production Space implementation, built for UI speed: it stores state in internal MutableThings (mutable mirrors of the immutable Things the rest of the engine sees) so updates avoid churning immutable copies on every keystroke. By default it is memory-only, with an optional DumbStorage constructor that also dumps state for restart recovery. Nested Transaction batches edits atomically and Helper carries the internal bookkeeping; setSpec/initialize wire the Spec before use.

## How it fits

AppSource constructs and owns this as the app's read model; SqliteBinaryStorage plugs in as its DumbStorage on Android. MutableSpaceTest reruns the whole SpaceTest suite against it to prove it honors the Space contract. End to end, saving a URL walks through this file like this: the app builds a generated save Action (inputs: the URL) and hands it to the source, which applies it to the local Space first via the Spec so the UI updates instantly (this optimistic local apply is why saves feel immediate), then queues the Action for the remote transport; the server reply is parsed back into Things that are imprinted into the Space through the Resolver, and every subscriber watching those Things receives a Changes update and re-renders.

## Key pieces

- `setSpec/initialize` — one-time wiring of the Spec and stored state before the space serves reads
- `remember/forget/get/getAll` — the Holder-lifetime plus read surface UI code uses
- `imprint overloads` — ingest paths for fresh server data, each triggering derive and subscriber fan-out
- `derive/flagChanged` — recomputation triggers for derived fields after inputs change
- `Transaction/Helper` — atomic batching plus internal bookkeeping for complex updates

## Junior notes

- setSpec/initialize ordering matters: the space cannot interpret stored data without its Spec, so construct-then-initialize before any read.
- Speed comes from mutability inside, immutability outside: never leak a MutableThing reference past the space boundary.
