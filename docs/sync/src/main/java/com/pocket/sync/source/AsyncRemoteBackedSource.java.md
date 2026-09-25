# sync/src/main/java/com/pocket/sync/source/AsyncRemoteBackedSource.java

## What this is

The async flavor of a remote-backed source: the SynchronousRemoteBackedSource operations plus callback delivery, and an extra bindLocal helper that fetches a Thing and subscribes for future changes in one step. Bind-then-subscribe as one call avoids the race where a change lands between an initial read and a separate subscribe.

## How it fits

AppSource implements this contract toward the UI layer: screens bind the Things they display and get both the current value and a live subscription from one call. The blocking twin is SynchronousRemoteBackedSource.

## Key pieces

- `bindLocal(thing, subscriber, errorCallback)` — one-shot read plus ongoing subscription, closing the read-then-watch race window

## Junior notes

- Prefer bindLocal over manual get-then-subscribe in UI code; the two-step version can miss an update that lands in between.
