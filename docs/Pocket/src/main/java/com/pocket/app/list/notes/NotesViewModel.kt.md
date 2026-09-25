# Pocket/src/main/java/com/pocket/app/list/notes/NotesViewModel.kt

## What this is
Data pipe for the Notes panel on the Saves tab. It exposes a paginated stream of note previews and a one-shot event when a note is tapped.

## How it fits
Created by the Notes composable via Hilt DI. `initialize(myListViewModel)` waits until `MyListViewModel.uiState` reports `notesVisible`, then collects `NotesRepository.getNotes()` (a PagingData stream, i.e. pages loaded on demand) and re-emits it mapped to `NoteUiState` rows. `onNoteClicked` emits `Event.GoToNoteDetails`, which the host fragment turns into Navigation-component travel to `NoteDetailsFragment`.

## Key pieces
- `_notes: MutableSharedFlow<PagingData<NoteUiState>>` (`replay = 1`, drop-oldest) — WHY: always replays the latest page to new collectors (rotation-safe) without replaying stale events.
- `initialize(myListViewModel)` + `whenNotesVisible` — WHY: defers the database paging query until the Notes filter is actually on screen, saving work on every normal list visit.
- `onNoteClicked(noteId)` — WHY: the tap exit point; emits the navigation event on a `viewModelScope` coroutine (a background task tied to the ViewModel's lifetime).
- `NoteUiState.from(note)` — WHY: maps the DB model to list-row data (id, title, content preview, updated-at).

## Junior notes
- `SharedFlow` (event stream, no current value) is used instead of `StateFlow` (state holder) because `PagingData` is a stream of pages, not a single value — do not `.value` it.
- `collectLatest` in `whenNotesVisible` restarts the block on every UI-state emission, so the repository query re-subscribes if visibility toggles.
