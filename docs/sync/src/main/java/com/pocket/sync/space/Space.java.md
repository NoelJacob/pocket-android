# sync/src/main/java/com/pocket/sync/space/Space.java

## What this is

The local store contract: a Space remembers Things (imprint for everything seen, remember per Holder for what must stay available, get to read back, forget to release, derive to recompute derived fields). Nested Condition/Edit types express conditional reads and batched edits; Volatile/Persisted mark lifetime expectations. The mental model is a smart in-memory cache keyed by Thing identity, not a database: queries are by identity and holders, and the server remains the source of truth.

## How it fits

AppSource owns one (a MutableSpace in production) as the UI's read model: every screen reads through get, every server reply lands via imprint, and subscriptions fire off the back of these calls. End to end, saving a URL walks through this file like this: the app builds a generated save Action (inputs: the URL) and hands it to the source, which applies it to the local Space first via the Spec so the UI updates instantly (this optimistic local apply is why saves feel immediate), then queues the Action for the remote transport; the server reply is parsed back into Things that are imprinted into the Space through the Resolver, and every subscriber watching those Things receives a Changes update and re-renders.

## Key pieces

- `imprint` — ingests seen Things so remembered ones update and derived state recomputes
- `remember/forget/get` — Holder-scoped lifetime control plus identity reads
- `derive` — recomputes derived fields whose inputs changed
- `Condition/Edit/Selector` — conditional and batched access shapes for advanced reads/writes
- `Volatile/Persisted` — lifetime expectations for transient versus restart-surviving entries

## Junior notes

- Imprint everything you see, remember only what you need: imprinting is cheap and keeps data fresh, remembering is a lifetime commitment.
- The Space is a cache, not the truth: server responses can replace anything here, so never treat local state as authoritative.
