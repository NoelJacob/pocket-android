# Pocket/src/main/java/com/pocket/util/android/view/chip/ChipLayout.java
## What this is
A wrapping container that shows strings as tappable chip views, flowing onto multiple lines like words in a paragraph. It maps each child view to its text and forbids raw `addView`/`removeView` so the map never desyncs.
For example, `setAdapter((text, parent) -> tagView)` then `addChip("travel")` appends a pill; tapping it fires `OnItemClickListener`.

## How it fits
Extends the third-party `FlowLayout` for wrapping measure. Used directly by `SuggestedTagsModule` (suggested-tag pills with retry/loading states) and as the base of `ChipEditTextInternal` (editable chips plus trailing input).

## Key pieces
- `setAdapter(ChipViewCreator)` / `getAdapter()`: factory turning text into views. WHY they exist: separate pill styling (e.g. `SuggestedTagView` vs `TagBadgeView`) from layout logic.
- `addChip(text[, position])` / `addChip(Chip[, position])`: insertion paths. WHY they exist: text variant mints via the adapter; `Chip` variant supplies a custom view.
- `removeChip(text)` / `removeChipAt` / `removeAllChips`, `getChipCount()` / `getChipText()`: removal and inspection. WHY they exist: the only legal mutations, keeping the view-to-text map and click wiring intact.
- `Chip` + `ChipViewCreator` interfaces: `getView(text, parent)` / `getText()`. WHY they exist: let any view pose as a chip while the layout tracks its string.
- `setOnItemClickListener` / `OnItemClickListener.onItemClick(parent, view, position)`: tap callbacks. WHY they exist: chips act like buttons (e.g. tapping a suggestion adds the tag).
- `mChipText` map + `mIsModifyingChildren` guard: WHY they exist: preserve text identity across layout passes and reject direct child edits.

## Junior notes
- Call `getChipCount()`, not `getChildCount()`: in editable use the input view is also a child, so child count over-reports chips.
- `removeChip(text)` matches case-insensitively on the displayed text; duplicate labels remove the first match only.
- Direct `addView`/`removeView` throws `UnsupportedOperationException` on purpose; always go through the chip methods.
