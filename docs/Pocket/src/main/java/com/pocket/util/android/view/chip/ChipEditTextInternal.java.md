# Pocket/src/main/java/com/pocket/util/android/view/chip/ChipEditTextInternal.java
## What this is
The engine inside `ChipEditText`: a `ChipLayout` that keeps the real text input as its last child so chips always render before the caret. It owns focus, keyboard, validation, commit plumbing, and animated chip add/remove.
For example, when the inner input commits "travel", this class builds the pill view, inserts it before the input, and notifies chip listeners.

## How it fits
Instantiated and proxied by `ChipEditText` (which forwards `addChip`, `setValidator`, focus, and watcher calls here). It hosts a `ChipEditTextInternalInput` as the trailing child and implements `ChipLayout.OnItemClickListener` plus `OnChipCommitListener` to close the loop between typing and pills.

## Key pieces
- Hosting as last-child input (`ChipLayout` + trailing `EditText`): WHY it exists: guarantees visual order (chips, then caret) under the wrapping `FlowLayout` measure.
- `setAdapter` / `addChip` / `removeChip` / `clear` / commit paths: chip model mutations with scale animations (`ScaleAnimation`, `Interpolators`). WHY they exist: the animated source of truth `ChipEditText` exposes.
- Validator + `OnChipCommitListener` wiring: WHY it exists: enforces `StringValidator` before any typed text becomes a chip.
- Focus/keyboard/scroll handling (`refocus`, `unfocus`, `showKeyboard`, scroller sync): WHY it exists: makes the multi-view composite feel like one text field.
- `ChipEditTextWatcher` / `ChipInputCommitListener` fan-out: WHY they exist: separate "anything changed" from "user committed/removed a chip".

## Junior notes
- Never add views directly with `ViewGroup.addView()`; it throws by design. Use the `addChip`/`removeChip` methods so the text map and listeners stay consistent.
- This class is package-private and not for reuse; outside code talks to `ChipEditText`.
- Focus changes drive keyboard visibility here, so calling `showKeyboard` before the view is attached silently does nothing; order matters in fragment lifecycles.
