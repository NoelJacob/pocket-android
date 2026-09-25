# Pocket/src/test/java/com/pocket/app/list/tags/TagBottomSheetViewModelTest.kt
## What this is
Tests for `TagBottomSheetViewModel`, the tag picker/editor sheet over Saves. It proves the tag list orders untagged first, then recently-used, then alphabetical; the sheet closes when there are no tags; tapping a tag filters the list; edit mode enter/exit, rename-and-save, delete, dismiss, and not-tagged filtering all behave.
## How it fits
Guards production `TagBottomSheetViewModel`, coordinating `ListManager` (tag filter state), `TagRepository` (tag data), `StringLoader`, and `Tracker`. `tagFlow` (a `MutableSharedFlow`) doubles the repository tag stream and `listManagerSortFilterState` doubles filter state; `SharedFlowTracker` observes navigation events.
## Key pieces
- `setup()` — wires the ViewModel to the flow doubles; WHY: tag list and filter state are both streams.
- Ordering test — emits tags and asserts untagged / recent / alphabetical order in `tagsListUiState`; WHY: pins the display contract.
- `should close if there are no tags` — empty emission triggers Close navigation; WHY: sheet is pointless without tags.
- Click/edit/rename/delete/dismiss/not-tagged tests — verify `listManager.setTag`, repository rename/delete calls, edit-mode state transitions, and navigation; WHY: each row/mode maps to one manager or repository call.
## Junior notes
- `tagFlow` needs `extraBufferCapacity = 1` so `tryEmit` works without a collector race.
- Read `setup()` plus one scenario at a time; the file is ~280 lines with overlapping flow doubles.
