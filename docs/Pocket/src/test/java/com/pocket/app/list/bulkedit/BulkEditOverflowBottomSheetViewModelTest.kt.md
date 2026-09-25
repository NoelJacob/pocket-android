# Pocket/src/test/java/com/pocket/app/list/bulkedit/BulkEditOverflowBottomSheetViewModelTest.kt
## What this is
Tests for `BulkEditOverflowBottomSheetViewModel`, the overflow menu for multi-selected saves. It proves the title shows the selection count ("2 selected"), the favorite label flips based on selection state, and favorite / edit-tags / mark-viewed / mark-not-viewed actions call `ItemRepository` and emit the right navigation event.
## How it fits
Guards production `BulkEditOverflowBottomSheetViewModel`, opened from `MyListViewModel` bulk-edit mode. Uses mocked `ItemRepository`/`StringLoader`/`Tracker`, real item fixtures, and `SharedFlowTracker` to observe one-shot `navigationEvents`.
## Key pieces
- `setup()` — builds the ViewModel, attaches `SharedFlowTracker(subject.navigationEvents)`, stubs plural/string resources, calls `onInitialized` with two items; WHY: every test starts with a known two-item selection.
- `onInitialized behavior` — re-initializes with one item and asserts title/count and favorite-vs-unfavorite label; WHY: label depends on aggregate selection state.
- `on favorite clicked behavior` — verifies `itemRepository.favorite` and `Close` navigation; WHY: action then dismiss.
- Edit-tags / viewed / not-viewed tests — verify repository calls and navigation; WHY: each overflow row is action-plus-close.
## Junior notes
- `StringLoader` is mocked because real string/plural lookup needs Android resources; the `slot` capture fakes `"N selected"` formatting.
- `SharedFlowTracker.lastValue` reads the most recent navigation event; the tracker collects on Main, which `BaseCoroutineTest` provides.
