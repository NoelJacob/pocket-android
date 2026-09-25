# Pocket/src/main/java/com/pocket/repository/SavesRepository.kt
## What this is
A one-method read model for "recent saves": the newest N unread items. It exposes the raw sync-engine `Saves` object as a live stream.
## How it fits
Used by widgets, badges, or surfaces that need a quick "what did I just save?" peek without the full Saves list machinery. It builds a `saves` query (`count`, `UNREAD` state, `NEWEST` sort) and observes the local cache via `bindLocalAsFlow()` (a Flow, i.e. observable stream, re-emitting on local changes).
## Key pieces
- `getRecentSavesAsFlow(count)` — the only entry point; `count` caps how many items the server returns and the cache tracks.
## Junior notes
- It filters to `UNREAD` only, so archived items never appear here; use `ItemRepository` for archive/all-items lists.
- It returns raw `Saves`, not `DomainItem`s; map to domain models at the call site if you need display fields.
