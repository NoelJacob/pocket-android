# Pocket/src/main/java/com/pocket/app/list/tags/TagsAdapter.kt

## What this is
RecyclerView adapter for the rows inside the tag-picker bottom sheet: one editable tag row per tag plus a leading "not tagged" row.

## How it fits
Created by `TagBottomSheetFragment` with the shared `TagBottomSheetViewModel`. It re-renders on every `tagsListUiState` emission; row taps and text edits call straight back into the ViewModel (`onTagClicked`, `onNotTaggedClicked`, `onTagEdited`, `onDeleteTagClicked`).

## Key pieces
- `getItemViewType` / `onCreateViewHolder` — WHY: two row kinds (`TAG` vs `NOT_TAGGED`) inflate different bindings from the same adapter.
- `TagViewHolder.bind` — WHY: in normal mode the invisible `clickableView` overlay takes taps to filter; in edit mode the text field enables, the trash icon appears, and a `TextWatcher` stages renames into the ViewModel.
- `NotTaggedViewHolder.bind` — WHY: the "show untagged items" shortcut row; disabled (non-clickable) while in edit mode.
- `repeatOnCreated` collector — WHY: `notifyDataSetChanged` on every state emission; the list is short and rows change type, so fine-grained diffing buys nothing.

## Junior notes
- The `TextWatcher` is removed before `setText` and re-added after — without that, recycling a row would stage phantom renames during bind.
- `clickableView` is a transparent overlay used because the editable text field steals touches; visibility flips with `editable` so taps land in the right place per mode.
