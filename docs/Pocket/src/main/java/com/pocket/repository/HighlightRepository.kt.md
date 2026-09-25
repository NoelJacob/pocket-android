# Pocket/src/main/java/com/pocket/repository/HighlightRepository.kt
## What this is
Adds, observes, and deletes yellow highlights on a saved article. Writes go out as sync-engine "actions" (queued mutations sent to the server); reads are a live `Flow` (observable state stream) of the item's highlights.
## How it fits
Used by the reader screen: swipe-to-highlight calls `addHighlight()`, the highlight list observes `getHighlightsFlow(url)`, trash calls `deleteHighlight()`. It converts sync-engine `Annotation` objects to domain `Highlight` via `DomainItem`, so the UI never sees raw sync types.
## Key pieces
- `addHighlight(patch, text, itemUrl)` — builds an `Annotation` with a random UUID (unique id) and `version = 2`, then `pocket.update()` queues an `add_annotation` action; WHY `update` (fire-and-forget): the local cache updates optimistically and syncs later.
- `getHighlightsFlow(url)` — `bindLocalAsFlow()` on the local `Item` by URL, mapped to its highlight list; WHY a Flow: the list re-emits whenever the local item changes.
- `deleteHighlight(highlightId, itemUrl)` — queues a `delete_annotation` action and `.await()`s it; WHY awaited: the caller wants confirmation the delete landed.
## Junior notes
- `pocket.update()` vs `pocket.sync(...)`: `update` applies locally and syncs in the background, while `sync(...).await()` waits for the round trip. Add is optimistic; delete waits.
- `patch`/`version` come from the reader's JS anchoring logic; version 2 is hardcoded, so a future format change must update both sides.
