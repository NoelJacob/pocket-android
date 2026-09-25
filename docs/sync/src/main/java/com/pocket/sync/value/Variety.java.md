# sync/src/main/java/com/pocket/sync/value/Variety.java

## What this is

Marker interface for variety types: schema unions where a value is one Thing out of a limited declared set (a feed slot holding either an article card or a video card, for example). It is GraphQL-union-shaped but codegen'd into the sync type system, unlike interfaces which are open-ended contracts. OpenParser dispatches these at parse time via the _type discriminator.

## How it fits

VarietyGenerator emits the per-union plumbing; VarietyExample and UnknownVarietyExample in the examples show the known and forward-compatible cases. OpenUsages fixtures exercise them in tests.

## Key pieces

- `variety marker` — the tag identifying union-typed values for parsing, generation, and dispatch

## Junior notes

- Varieties are closed sets with forward-compat pressure: servers adding a member must not crash old clients (see UnknownVarietyExample).
