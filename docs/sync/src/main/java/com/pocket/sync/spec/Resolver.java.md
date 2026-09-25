# sync/src/main/java/com/pocket/sync/spec/Resolver.java

## What this is

The id-reconciliation step of the imprint phase: when a remote returns Things, their identities may be temporary, aliased, or nested differently than local state (a just-saved item gains its real server id, for example). The Resolver (add/addAll to stage candidates, resolve to unify them, reduce for compaction, with a ShallowList helper and a BASIC default instance) rewrites incoming graphs so local references point at the canonical Things before they enter the Space.

## How it fits

Client sources run this during imprint before anything becomes visible: AppSource takes a Resolver at construction (usually BASIC), and RemapTest plus the identity tests prove remapping preserves equality. End to end, saving a URL walks through this file like this: the app builds a generated save Action (inputs: the URL) and hands it to the source, which applies it to the local Space first via the Spec so the UI updates instantly (this optimistic local apply is why saves feel immediate), then queues the Action for the remote transport; the server reply is parsed back into Things that are imprinted into the Space through the Resolver, and every subscriber watching those Things receives a Changes update and re-renders.

## Key pieces

- `add/addAll` — staging incoming Things as resolution candidates
- `resolve/reduce` — unifying identities to canonical Things and compacting the result
- `BASIC/ShallowList` — the default resolver plus its lightweight candidate list

## Junior notes

- Resolution runs before visibility: bugs here show up as duplicated list rows (same item under two ids) rather than crashes.
