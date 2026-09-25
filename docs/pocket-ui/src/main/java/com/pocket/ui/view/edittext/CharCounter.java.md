# pocket-ui/src/main/java/com/pocket/ui/view/edittext/CharCounter.java

## What this is
A tiny label that shows "used / max" characters for another text field (for example "12 / 140"). It watches the target field and rewrites itself after every keystroke, turning apricot when the count hits the limit and staying grey otherwise. It also enforces the limit by installing an input LengthFilter on the watched field.

## How it fits
The counting half of CharCountEditText: that parent calls `bind().watchText(editText, length)` to attach this label to its field. It extends ThemedTextView (theme-aware TextView) styled as `Pkt_Text_Teeny_Tiny_Light`. Any other screen needing a standalone counter can drop in a CharCounter and call the same Binder; `clear()` detaches and blanks it for reuse.

## Key pieces
- `watcher` (TextWatcher): the internal listener; only `afterTextChanged()` does work, reading the watched field's length and rendering `R.string.quantity_count` ("N / M") with the limit-reached color swap.
- `Binder.watchText(tv, max)`: detaches from any previous field (removing the old listener), attaches to the new one, installs `InputFilter.LengthFilter(max)`, stores `maxLength`, and immediately refreshes the label; passing null detaches.
- `Binder.clear()`: blanks the label and unbinds via `watchText(null, 0)`.
- `maxLength` / `watchedText`: the cap and the currently observed field; WHY stored is so the label can refresh even when the callback fires with a null Editable.
- `init()`: applies the teeny-tiny light text appearance; colors come from `NestedColorStateList` (a helper resolving theme-aware color state lists): `pkt_themed_apricot_1` at the limit, `pkt_themed_grey_3` otherwise.

## Junior notes
- `watchText()` REPLACES the field's input filters with a single LengthFilter; any custom filters you set on the watched field are lost, so call this before adding your own (or re-add yours after).
- One CharCounter watches one field at a time; watching a second field silently detaches the first.
- The color change triggers only on exact equality (`length == maxLength`), so an over-limit paste cannot happen (the filter blocks it) and the warning never sticks spuriously.
