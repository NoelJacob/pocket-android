# sync/src/main/java/com/pocket/sync/source/RemoteBackedSource.java

## What this is

The marker for any Source that is backed by another Source, typically a client app store backed by a backend server. Like Source itself it declares no methods: the point is to label the relationship so engine code can reason about local-versus-remote layering. Concrete behavior lives in SynchronousRemoteBackedSource (blocking) and AsyncRemoteBackedSource (callbacks).

## How it fits

AppSource is the flagship implementation: a local Space backed by a server transport. End to end, saving a URL walks through this file like this: the app builds a generated save Action (inputs: the URL) and hands it to the source, which applies it to the local Space first via the Spec so the UI updates instantly (this optimistic local apply is why saves feel immediate), then queues the Action for the remote transport; the server reply is parsed back into Things that are imprinted into the Space through the Resolver, and every subscriber watching those Things receives a Changes update and re-renders.

## Key pieces

- `backed-by marker` — labels a source as local-state-fronting-remote so layering logic can treat it accordingly

## Junior notes

- Marker only: to add behavior, implement one of the sync/async remote-backed sub-interfaces rather than this directly.
