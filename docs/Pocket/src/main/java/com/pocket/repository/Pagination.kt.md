# Pocket/src/main/java/com/pocket/repository/Pagination.kt
## What this is
Shared cursor-paging vocabulary for every paged list: an opaque `Cursor` string plus helpers converting the server's `PageInfo` (has-next/has-previous plus boundary cursors) into those cursors, and one default page size.
## How it fits
Used by `NotesRepository`'s `Cache`/`Source`/`Mediator` (and any future paged repository) so all lists page the same way: the UI holds a `Cursor?`, `null` means "first page" or "no more pages". `DefaultPagingConfig` wires straight into Paging 3's `Pager`.
## Key pieces
- `Cursor` (`@JvmInline value class`: a type-safe String wrapper with no runtime cost) — WHY: a raw String could be confused with an id or URL; this type can't.
- `toPreviousPageCursor()` / `toNextPageCursor()` — return the boundary cursor only when the matching `hasPreviousPage`/`hasNextPage` flag is true, else null (end of list).
- `DefaultPageSize` (30) / `DefaultPagingConfig` (`enablePlaceholders = false`) — every page loads 30 rows and the list shows only loaded rows, no blank placeholder slots.
## Junior notes
- `toString()` on `Cursor` returns the raw value, which is what gets sent as the `after` argument; don't re-wrap it.
- A null next cursor means "stop paging", not "retry"; the Mediator reports `endOfPaginationReached = true` in that case.
