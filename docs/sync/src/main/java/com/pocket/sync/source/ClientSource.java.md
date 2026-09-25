# sync/src/main/java/com/pocket/sync/source/ClientSource.java

## What this is

The synchronous (blocking callers until done) client-side Source contract: the bundle of operations a user-facing app typically needs, such as syncing Things and Actions and observing state. Its async twin is AsyncClientSource; the two mirror each other so call sites can choose blocking or callback style without learning a different API. AppSource is the production implementation of the async side.

## How it fits

App and repository layers program against this (or the async variant) rather than against AppSource directly, which keeps them testable with fakes like InMemorySource. End to end, saving a URL walks through this file like this: the app builds a generated save Action (inputs: the URL) and hands it to the source, which applies it to the local Space first via the Spec so the UI updates instantly (this optimistic local apply is why saves feel immediate), then queues the Action for the remote transport; the server reply is parsed back into Things that are imprinted into the Space through the Resolver, and every subscriber watching those Things receives a Changes update and re-renders.

## Key pieces

- `sync operations` — the blocking entry points that apply actions locally and reconcile with the remote in one call
- `ClientSource vs AsyncClientSource` — same client capabilities, blocking versus PendingResult callback delivery

## Junior notes

- Blocking means never call these on Android's main thread; use AsyncClientSource or a background dispatcher for UI-triggered work.
