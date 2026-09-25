# Pocket/src/main/java/com/pocket/app/list/notes/NoteDetails.kt

## What this is
The note-reading screen: given a note id, it shows the note's title and markdown body full-page, with an up button to go back. A `NoteDetailsFragment` wrapper hosts Jetpack Compose UI (a declarative UI toolkit where screens are functions, not XML).

## How it fits
Reached from the Notes list via the Navigation component (fragment navigation graph): `NotesViewModel` emits `GoToNoteDetails`, the list navigates with the note id argument, and `NoteDetailsFragment` reads it via `navArgs`. `NoteDetailsViewModel` (Hilt DI) loads the note synchronously from `NotesRepository`; the composables render `Note` vs `Error` states.

## Key pieces
- `NoteDetailsViewModel.getNote(id)` — WHY: one-shot synchronous lookup mapping a `Note` to `NoteDetailsUiState.Note(title, doc)`, or `Error` when missing.
- `NoteDetailsUiState` (`Note` / `Error`) — WHY: the only two outcomes the screen can show; keeps the UI branch trivial.
- `NoteDetailsFragment` — WHY: bridges the fragment world (nav args, `AbsPocketFragment`) to Compose via `content { PocketTheme { ... } }`, wiring up-navigation to `navigateUp()`.
- `NoteDetailsScreen(noteId, ...)` vs `NoteDetailsScreen(note, ...)` — WHY: the outer overload resolves data; the inner one is a pure renderer, which is what previews exercise.
- `NoteDetails` / `NoteDetailsError` — WHY: scrolling title + markdown body (rendered through `ViewNoteDetailsContentBinding` in an `AndroidViewBinding`) vs the error message.

## Junior notes
- Compose here deliberately embeds an XML binding (`AndroidViewBinding`) for the markdown body — markdown rendering still lives in views, so do not rewrite it in Compose.
- `rememberScrollState` + `verticalScroll` makes the column scroll; without it long notes would clip.
