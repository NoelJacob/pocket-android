# Pocket/src/main/java/com/pocket/data/models/Note.kt
## What this is
A user-authored note: rich-text Markdown content (`doc`), a short `contentPreview` for list rows, an optional title and source URL, plus timestamps. `Id` is an inline value class (a type-safe wrapper with no runtime overhead) around the server id string.
## How it fits
`SyncEngineNotesRepository` (the `NotesRepository` implementation) pages, caches, creates, updates, and deletes notes through the sync engine, converting with `toNote()`. Consumed by the notes list and note editor screens.
## Key pieces
- `Note` — `doc` is the full editable Markdown, `contentPreview` is the list snippet; `createdAt` is currently always mapped to null (server value ignored), `updatedAt` drives sort order.
- `Id` — prevents mixing a note id up with an item id or URL at compile time.
- `toNote()` — maps sync-engine `Note` to domain `Note`; `toIdString()` unwraps `Id` back to the sync engine's id type for requests.
## Junior notes
- Markdown here means lightweight formatting syntax (`**bold**`, lists); render `contentPreview` as styled text, not raw Markdown.
- `createdAt` is always null today, so never sort or display by it; use `updatedAt`.
