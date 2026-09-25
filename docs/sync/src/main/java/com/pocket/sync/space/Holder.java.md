# sync/src/main/java/com/pocket/sync/space/Holder.java

## What this is

Represents one owner of cached Things: whoever wants entries to stay available in the Space (a screen, a repository, the persistent cache) holds them through a Holder, and the Space keeps a Thing only while at least one Holder remembers it. Holders are identified by key(), so recreating an equal key refers to the same owner; persistent() versus session() distinguish cache entries that survive restarts from those dropped on process death. The Hold annotation marks hold sites.

## How it fits

Every remember/forget call passes one: UI layers typically use session holders tied to screen lifetime, while the disk cache uses a persistent holder. Forgetting with the wrong key silently keeps data alive, which is the usual suspect in Space growth bugs.

## Key pieces

- `key/equals/hashCode` — owner identity: same key means same owner across recreations
- `persistent()/session()/from()` — factories for restart-surviving versus transient owners plus custom keys
- `hold/Hold` — the declaration of what one owner keeps alive

## Junior notes

- Pair every remember with a forget on the same key when the owner dies; unbalanced holders leak Things until the process ends.
