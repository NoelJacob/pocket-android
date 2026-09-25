# Pocket/src/main/java/com/pocket/app/list/tags/TagBottomSheetViewModel.kt

## What this is
State and logic for the tag-picker bottom sheet: the tag list (recently used first), tag selection, in-place renaming, and tag deletion.

## How it fits
Scoped to `TagBottomSheetFragment` via Hilt DI (constructor params provided automatically). `onInitialized` collects `TagRepository.getTagsAsFlow()`; picking a row writes through to `ListManager` (`setTag` / `addFilter(NOT_TAGGED)`), which refilters the main list. Rename/delete go to `TagRepository`. One-shot `navigationEvent` flow (a `SharedFlow`, i.e. an event stream) tells the fragment to close or confirm deletion.

## Key pieces
- `onInitialized(savesTab)` — WHY: builds `tagList` with up to 3 recently-used tags pinned on top, then the rest alphabetically; auto-closes the sheet when there is nothing to show.
- `onTagClicked` / `onNotTaggedClicked` — WHY: the two filter exits; both close the sheet after setting the `ListManager` filter.
- `onEditClicked` / `onTagEdited` / `onSaveClicked` / `onCancelClicked` — WHY: rename mode; edits accumulate in `tagEditMap` (old→new) and only changed pairs are sent to `editTags` on save.
- `onDeleteTagClicked` / `onDeleteTagConfirmed` / `onDeleteCanceled` — WHY: stashes `tagToDelete`, asks the fragment to confirm, then deletes.
- `invalidateTagListItemUiState` — WHY: rebuilds row states (including the leading not-tagged row) whenever the list or edit mode changes.
- `onDismissed` — WHY: clears the transient tag filter if the sheet closes without a selection, so the list never strands on an empty filter.

## Junior notes
- `StateFlow` (`uiState`, `tagsListUiState`) holds renderable state; `SharedFlow` (`navigationEvent`) fires single events — collect events with a one-shot collector or a rotation will re-fire the delete dialog.
- Renames are staged, not applied per keystroke: `tagEditMap` is the draft, `editTags` is the commit.
