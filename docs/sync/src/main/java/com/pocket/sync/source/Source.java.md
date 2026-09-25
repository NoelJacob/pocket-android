# sync/src/main/java/com/pocket/sync/source/Source.java

## What this is

The base marker interface for the entire data layer: it declares no methods and exists only to tag a class as being a Source, which is anything the app reads state from or writes intents to. Real capability comes from the sub-interfaces layered on top (syncing, subscribing, persisting, remote backing). Keeping the root empty lets each source shape expose only the operations it actually supports.

## How it fits

Everything source-shaped implements this, from the in-memory test doubles (InMemorySource) through the app workhorse (AppSource) to the server transports. When you see a method accept a Source, it only needs the concept, and callers pass whichever richer implementation they hold.

## Key pieces

- `Source (marker)` — the shared tag that lets engine code treat local stores, remotes, and combined app sources uniformly

## Junior notes

- An empty interface looks pointless until you need it: it is what lets generic helpers accept any source without depending on every capability interface.
