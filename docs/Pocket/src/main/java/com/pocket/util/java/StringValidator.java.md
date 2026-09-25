# Pocket/src/main/java/com/pocket/util/java/StringValidator.java
## What this is
A one-method contract for validating a text field: return `null` when the value is fine, or an error message to show the user when it is not. The null-means-valid convention keeps the happy path cheap.
For example, a tag field's validator returns `"Too long"` for overlength input, and the chip field refuses to mint a chip until `validate()` returns null.

## How it fits
Consumed by `ChipEditText.setValidator()` (forwarded to its internal input): before auto-committing typed text into a chip, the input consults the validator, and `TagEditTextModule` supplies tag rules through this seam.

## Key pieces
- `validate(String value): String`: null if valid, else a user-facing message. WHY it exists: merges the validity test and the error text into one call so the UI can display the result directly.

## Junior notes
- The return contract is inverted from boolean validators: `null` means valid. Always null-check the result rather than treating a string as failure alone.
- Keep `validate()` fast and side-effect free; it runs on the input path, potentially per keystroke or commit.
