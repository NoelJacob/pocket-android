# Pocket/src/main/java/com/pocket/app/list/list/overflow/ItemOverflowBottomSheetViewModel.kt

## What this is
Logic behind the single-item overflow sheet: it labels each row correctly for the item's current state (viewed?, archived?) and executes the chosen action.

## How it fits
Scoped to `ItemOverflowBottomSheetFragment` via Hilt DI. `onInitialized(item, savesTab)` snapshots the item and builds the title/thumbnail/labels/icons; button taps call `ItemRepository` (toggle viewed) or the `UndoBar` (archive/delete, which offer undo) and then flip `screenState` so the fragment dismisses or opens tags.

## Key pieces
- `onInitialized` — WHY: derives viewed text/icon (`Mark as viewed` vs `Mark as not viewed`) and archive text/icon (`Archive` vs `Move to My List`) from `item.viewed` and `item.status`.
- `onViewedClicked` — WHY: toggles viewed state directly, then closes.
- `onTagClicked` — WHY: no data change here; just requests the tag screen.
- `onArchiveClicked` — WHY: archived items are re-added immediately, while archiving goes through `undoable.archive(...)` so the user gets an Undo snackbar.
- `onDeleteClicked` — WHY: routes through `undoable.delete` for the same undo safety.
- `ItemOverflowBottomSheetUiState` / `ItemOverflowBottomSheetScreenState` — WHY: persistent display data vs one-shot navigation (`SHOWING` → `CLOSING` / `OPEN_TAG_SCREEN`).

## Junior notes
- Archive and delete use the `UndoBar` path (undoable), but viewed-toggle does not — that asymmetry is deliberate: archive/delete are destructive, viewed is trivially reversible.
- `viewed == true` uses nullable comparison because the API field is optional; a missing value is treated as unviewed.
