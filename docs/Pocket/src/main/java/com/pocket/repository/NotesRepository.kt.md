# Pocket/src/main/java/com/pocket/repository/NotesRepository.kt
## What this is
CRUD plus infinite scroll for user notes, backed by the Paging 3 library (`Pager`, `PagingSource`, `RemoteMediator`: Google's paging stack where the Mediator loads network pages and the Source serves cached rows). A small in-memory `Cache` of pages fronts the network so edits feel instant.
## How it fits
Used by the notes list and editor screens. `getNotes()` returns a `Flow<PagingData<Note>>` (a stream of pages) the UI collects; create/update/delete mutate the in-memory cache immediately and queue a sync-engine action (`pocket.send`) in the background. Reads are remote-first GraphQL (`notes` connection with sort/filter/pagination); the local-vs-remote story here is optimistic cache first, server authoritative on refresh.
## Key pieces
- `NotesRepository` (interface) — `getNotes`, `getNote`, `createNote`, `updateNote`, `deleteNote`; `SyncEngineNotesRepository` is the implementation.
- `getNotes()` — builds a `Pager` with `DefaultPagingConfig` wired to `Source` (cache) + `Mediator` (network); WHY: scrolling appends pages without the UI managing cursors.
- `createNote` / `updateNote` / `deleteNote` — write to the in-memory `Cache` first (so the list updates now), then `pocket.send` the matching action; create requires a client-generated id plus Markdown doc.
- `Cache` — holds loaded `Page`s, serves `pagingSource()` snapshots, and `invalidateSources()` on every mutation so the pager re-reads; `set()` prepends to page 0 (newest first).
- `Source` — `PagingSource` over a frozen page snapshot; `getRefreshKey` returns null so refresh always restarts from the first page.
- `Mediator` — `RemoteMediator` doing forward-only paging: `REFRESH` loads page one (replacing cache), `APPEND` follows `nextKey`, `PREPEND` is a no-op success; queries sort by `UPDATED_AT` desc, exclude deleted, and use `first`/`after` cursor pagination; `SKIP_INITIAL_REFRESH` means the cached pages show first.
## Junior notes
- `Cache.set()` does `pages[0]` directly and will crash when no page has loaded yet; never create a note before the first page arrives.
- Pages are snapshot lists (`pages.toList()`); mutating the cache never affects an already-created `Source`, which is why every mutation must call `invalidateSources()`.
