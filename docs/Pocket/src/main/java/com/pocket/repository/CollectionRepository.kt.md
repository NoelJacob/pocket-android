# Pocket/src/main/java/com/pocket/repository/CollectionRepository.kt
## What this is
Fetches one Pocket Collection (curated story bundle) by URL. It extracts the slug (last path segment) from the URL and issues a `collectionBySlug` query, then maps the result to the domain `Collection`.
## How it fits
Called by the collection detail screen/ViewModel when the user opens a collection link (often from Home). It reads through the sync engine (`Pocket`): `remember()` pins the query in a session-scoped `Holder` (a cache bucket), then `get()` returns the server data. Downstream is `toDomainCollection()` and the UI.
## Key pieces
- `holder` (`Holder.session("collectionsSession")`) — session-scoped cache bucket; WHY: the fetched collection lives only for this app session, not persisted to disk.
- `getCollection(url)` — parses the slug, builds the query, pins it with `remember()`, and returns the mapped collection; the trailing `!!` crashes if the server returns no collection.
## Junior notes
- `remember()` before `get()` matters: it tells the sync engine to cache/track this query; skipping it means no local subscription.
- The `!!` means an unknown slug or network miss throws instead of returning null; callers must handle that exception.
