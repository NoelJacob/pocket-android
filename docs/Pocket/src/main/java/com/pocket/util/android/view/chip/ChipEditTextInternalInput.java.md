# Pocket/src/main/java/com/pocket/util/android/view/chip/ChipEditTextInternalInput.java
## What this is
The actual keystroke-level `EditText` inside the chip field. A `TextWatcher` (a callback fired on every text change) scans each edit for commit characters, pasted delimiters, and the done/enter key, then asks to mint chips for the completed segments.
For example, pasting `"a,b,c"` splits on commas and requests three chips instead of leaving raw text.

## How it fits
Created by `ChipEditTextInternal` as its trailing child; commit requests flow up through `OnChipCommitListener` to the internal layout, which validates via `StringValidator` and inserts pills. `ChipEditText` never exposes this class; it only forwards hints, filters, and text through it.

## Key pieces
- Commit-char scanning in the text watcher (typed char, pasted span, enter/done action): WHY it exists: the trigger that turns "user finished a token" into chip creation.
- `InputConnectionWrapper` override: WHY it exists: catches soft-keyboard delete/enter events (which bypass `onKeyDown`) so backspace into chips and done-to-commit work on all keyboards.
- `OnChipCommitListener`: the up-call requesting chip creation. WHY it exists: keeps policy (validate, insert, animate) in the parent while detection stays here.
- `commitPending()` support: WHY it exists: lets hosts flush half-typed text into a chip on save.
- `StringBuilders` usage for splitting: WHY it exists: avoids garbage while slicing pasted or multi-delimiter input.

## Junior notes
- Soft keyboards vary wildly: always handle both `OnEditorActionListener` (done/enter) and the `InputConnection` path, or some keyboards silently skip commits.
- Mutating text inside a `TextWatcher` callback re-enters the watcher; the class guards against recursion, so do not add unguarded edits of your own.
- `FormFactor` checks here adapt input types for TV vs touch devices; test chip entry on a real soft keyboard, not just hardware keys.
