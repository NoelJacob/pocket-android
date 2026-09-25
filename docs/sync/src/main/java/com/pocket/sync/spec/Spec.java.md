# sync/src/main/java/com/pocket/sync/spec/Spec.java

## What this is

The domain-logic hub of one API: it declares which Things and Actions exist (nested Things/Actions registries), how Actions change the Space (apply, returning a RemotePriority for send urgency), and how derived fields recompute (nested Derive). Everything engine-generic (Space, transports, subscriptions) calls into this for everything product-specific. Concrete specs extend the generated BaseSpec and fill in only the action/derive methods codegen could not write.

## How it fits

AppSource holds the Spec and consults it on every apply/derive/resolve step; Pocket's real spec and the test SyncTestsSpec both extend generated bases exactly this way. End to end, saving a URL walks through this file like this: the app builds a generated save Action (inputs: the URL) and hands it to the source, which applies it to the local Space first via the Spec so the UI updates instantly (this optimistic local apply is why saves feel immediate), then queues the Action for the remote transport; the server reply is parsed back into Things that are imprinted into the Space through the Resolver, and every subscriber watching those Things receives a Changes update and re-renders.

## Key pieces

- `Things/Actions` — the registries of available state shapes and intents for this API
- `apply` — the product logic turning one Action into Space changes plus a send-urgency hint
- `Derive` — the recomputation rules for fields computed from other fields

## Junior notes

- New product behavior means: schema first, regenerate, then implement the new apply/derive methods here; never hand-write what codegen owns.
