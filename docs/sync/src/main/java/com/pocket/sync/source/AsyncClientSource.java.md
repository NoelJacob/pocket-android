# sync/src/main/java/com/pocket/sync/source/AsyncClientSource.java

## What this is

The asynchronous twin of ClientSource: the same client-side Source capabilities, but every operation returns a PendingResult callback handle instead of blocking. This is the interface AppSource implements, and therefore the one real app code talks to most. Kotlin callers usually skip the raw callbacks via the await() helper in SyncExtensions or the suspending wrappers in AndroidAsyncClientSourceExtensions.

## How it fits

ViewModels and repositories hold this (often an AppSource instance) to sync saves, fetch lists, and bind UI to Things. End to end, saving a URL walks through this file like this: the app builds a generated save Action (inputs: the URL) and hands it to the source, which applies it to the local Space first via the Spec so the UI updates instantly (this optimistic local apply is why saves feel immediate), then queues the Action for the remote transport; the server reply is parsed back into Things that are imprinted into the Space through the Resolver, and every subscriber watching those Things receives a Changes update and re-renders.

## Key pieces

- `async sync/bind operations` — client capabilities delivered through PendingResult callbacks so the UI thread never blocks
- `mirror of ClientSource` — keeps blocking and callback APIs symmetric so behavior is identical whichever style a call site uses

## Junior notes

- Every PendingResult you receive should reach success/failure or be abandoned; dropping one silently can leave queued work nobody observes.
