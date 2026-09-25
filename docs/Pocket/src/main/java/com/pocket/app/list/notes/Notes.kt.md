# Pocket/src/main/java/com/pocket/app/list/notes/Notes.kt

## What this is
The Notes panel embedded inside the Saves tab when the Notes filter is selected. It is a Compose screen showing a paginated list of notes (title, HTML preview, date) with loading, empty, and error states plus a create-note button.

## How it fits
Hosted by the My List screen; the outer `Notes(findNavController)` composable pulls `MyListViewModel` (to know when notes are visible) and `NotesViewModel` (for the paging data). Tapping a note emits `GoToNoteDetails`, which the host turns into navigation to `NoteDetailsFragment`. Data arrives as Jetpack Paging items (`LazyPagingItems`, i.e. an incrementally loading list) mapped to `NoteUiState`.

## Key pieces
- `Notes(findNavController, ...)` — WHY: wiring layer; calls `viewModel.initialize(myListViewModel)` once and collects `notes` paging flow plus navigation `events`.
- `Notes(lazyPagingNotes, onNoteClick, onCreateNoteClick)` — WHY: pure state switch mapping Paging `LoadState` (refresh/append states) onto `Initial/List/Empty/Error/Loading`.
- `NotesList` — WHY: the `LazyColumn` (Compose scrolling list) of `NoteRow`s with an append spinner/error at the bottom.
- `NoteRow(title, content, date)` — WHY: one row; renders the HTML preview `Spanned` and formatted date.
- `NotesEmpty` / `NotesError` / `NotesLoading` — WHY: the three non-list states with matching art and retry/create actions.
- `asLazyPagingItems` — WHY: test/preview-only shim that wraps a plain list as paging data so `@Preview`s render without a database.

## Junior notes
- Notes only load while `screenState.notesVisible` is set — `NotesViewModel.initialize` gates collection on that flag, so the panel costs nothing when hidden.
- `content` here is a `Spanned` (styled HTML), not markdown — the list shows a preview; the details screen renders the full markdown doc.
