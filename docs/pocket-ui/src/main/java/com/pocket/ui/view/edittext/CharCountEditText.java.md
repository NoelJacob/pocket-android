# pocket-ui/src/main/java/com/pocket/ui/view/edittext/CharCountEditText.java

## What this is
A text field with a live character counter underneath it (for example "42 / 280"). The user types in the top field while the label below updates on every keystroke and turns apricot-colored when the limit is reached; input is hard-capped at the max length. It also exposes helpers to show or hide the soft keyboard on demand.

## How it fits
Used for composed inputs with limits (renames, notes, captions). It extends ThemedLinearLayout (vertical) and inflates `view_char_count_edittext`, which contains the EditText (`R.id.edit_text`) and a CharCounter (`R.id.char_count`). Hosts configure it through `bind()`: `maxLength(n)` wires the counter and the length cap, `textChanged(watcher)` attaches an optional extra TextWatcher (an interface with before/on/after-text-changed callbacks), and `clear()` resets both halves.

## Key pieces
- `editText` / `charCount`: the two inflated children; WHY they are looked up once in `init()` is so the Binder never touches layout inflation.
- `Binder.maxLength(length)`: delegates to `CharCounter.bind().watchText(editText, length)`, which both starts the live count and installs the `LengthFilter`.
- `Binder.textChanged(w)`: swaps the single hosted external watcher (removing the old one first) so callers never stack duplicate callbacks.
- `Binder.clear()`: empties the text, clears the counter, and re-fires `textChanged(null)` bookkeeping.
- `showKeyboard()` / `hideKeyboard()`: request focus and force the soft input open, or hide it via its window token.
- `getEditText()`: escape hatch exposing the raw EditText for input type, hint, or IME options the Binder does not cover.

## Junior notes
- Only one external TextWatcher is tracked (`watcher` field): calling `textChanged()` twice replaces, not adds; attach multiple watchers via `getEditText().addTextChangedListener()` if you truly need two.
- The counter owns the `LengthFilter`, so setting your own input filters on the EditText afterwards will wipe the cap; set the max length last.
- This is a compound view, not an EditText subclass: it cannot be passed where an EditText is required, use `getEditText()` there.
