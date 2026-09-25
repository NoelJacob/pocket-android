# Pocket/src/main/java/com/pocket/util/android/view/chip/ChipEditText.java
## What this is
An `EditText`-like field (Android's text-input widget) that renders confirmed tokens as "chips" (little tag pills) before the typing area, used for tag entry. Typing a delimiter (comma, done key) auto-converts the pending text into a chip, validated first.
For example, typing `travel,` turns `travel` into a pill and clears the input for the next tag.

## How it fits
Hosted by `ItemsTaggingFragment` (`tagsEditText`) and driven by `TagEditTextModule`, which wires the adapter, validator, and visibility. It is a `ThemedRelativeLayout` shell delegating almost everything to `ChipEditTextInternal`; touch outside children is funneled into the inner `EditText` so the whole area feels tappable.

## Key pieces
- `setAdapter(ChipViewCreator)` / `defaultAdapter()`: how text becomes a chip view (default builds a `TagBadgeView`). WHY they exist: required before auto-commit can mint chips.
- `addChip(...)` / `removeChip` / `removeAllChips` / `getChipCount` / `getChipText`: chip CRUD. WHY they exist: the programmatic model behind the pills.
- `setValidator(StringValidator)`: pre-commit check. WHY it exists: rejects bad tags with a user message before a chip is created.
- `commitPending()`: forces the current input into a chip now. WHY they exist: commits on done/paste or when saving with half-typed text.
- `setOnChipsChangedListener` / `addChipEditTextWatcher` / `addTextWatcher` / `setOnInputDoneListener`: the four notification levels (user chip commits, any content change, raw text, done key). WHY they exist: let hosts react at the right granularity.
- `showKeyboard` / `refocus` / `unfocus` / `moveCursorToEnd`, `setText/getText/setHint/setFilters/clearText/clear`: input affordances proxied inward. WHY they exist: make the composite behave like a plain `EditText`.

## Junior notes
- `databinding` (XML layouts bound to ViewModel fields) note: this is a code-built composite, not a bound layout; drive it through its methods, not XML expressions.
- `setOnClickListener()` is intentionally blocked (throws); listen via the chip/text listeners instead.
- Auto-commit only engages when commit chars are set via the `ChipEditText_chiptext_commitChars` XML attr; without an adapter plus validator it will misbehave.
