# Pocket/src/test/java/com/pocket/app/list/list/overflow/ItemOverflowBottomSheetViewModelTest.kt
## What this is
Tests for `ItemOverflowBottomSheetViewModel`, the per-item (...) menu in Saves. It proves initial UI state mirrors the item (title, favorite/viewed/archive labels), null-valued items do not crash, and each action (toggle viewed, edit tags, archive, re-add, delete) updates state or calls `ItemRepository` and closes or routes correctly.
## How it fits
Guards production `ItemOverflowBottomSheetViewModel`, opened from `MyListViewModel.onItemOverflowClicked` with a `fakeItem()` fixture. `ItemRepository`, `UndoBar`, `StringLoader`, `DrawableLoader`, and `Tracker` are relaxed mocks.
## Key pieces
- `setup()` — stubs drawables/strings and calls `onInitialized(item, tab)`; WHY: labels and icons come from loaders, not the item alone.
- `ui state reflects the items values` — asserts title/favorite/viewed/archive rows; WHY: menu is a direct reflection of item flags.
- `null values THEN nothing breaks` — initializes with an empty `Item.Builder().build()`; WHY: server items can lack fields.
- Per-action tests — verify viewed-toggle/archive/re-add call the repository and emit Close, edit-tags/delete emit open-tags navigation; WHY: each row is either a mutating action or a navigation.
## Junior notes
- `SavesTab.SAVES` vs archive changes archive/re-add labeling; tests fix the tab so label assertions are stable.
- `DrawableLoader` is mocked because real icon loading needs Android resources.
