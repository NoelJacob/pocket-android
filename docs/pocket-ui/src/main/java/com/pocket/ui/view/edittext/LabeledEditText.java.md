# pocket-ui/src/main/java/com/pocket/ui/view/edittext/LabeledEditText.java

## What this is
A text field with a floating label above it and a thin underline below (the Material-style labeled input). The user sees the label float above the field, types into the inner EditText, and gets an error state that tints both the underline and the text when validation fails. The underline can be hidden for borderless variants, and a right-side drawable (such as a clear or show-password icon) can be set from XML.

## How it fits
Used for forms (login, signup, edit screens) wherever a labeled input with error styling is needed. It extends ThemedTextInputLayout (Pocket's theme-aware wrapper around Material's TextInputLayout, which provides the floating-hint machinery) and builds its inner ThemedEditText plus underline View programmatically in `init()`, rather than inflating a layout. Hosts drive it via `bind()`: `label()`, `text()`, `underline()`, `errorState()`, `clear()`; it reports font visual margins through the VisualMargin interface (top/bottom inset hints used to align text rows tightly).

## Key pieces
- `editText` (inner ThemedEditText): the real input, created with `Pkt_EditTextAppearance` theme wrapper; its id is reset to NO_ID because inheriting the parent's id from `attrs` would cause duplicate-id crashes.
- `line` (underline View): a 1dp view tinted by `pkt_edittext_underline` state list; `setErrorColors()` activates both line and field for the error look.
- `setHintLabel()`: puts the hint on the outer layout (so it floats) and clears the inner hint that would otherwise duplicate it.
- `Binder.label()` / `text()` / `underline(show)` / `errorState(error)` / `clear()`: the full configuration surface; `clear()` also resets the error state.
- `isErrorState()`: reports error via the inner field's activated flag.
- `setOnFocusChangeListener()`: reroutes to the inner field but reports this view (not the inner field) as the focus source, so callers see a stable view identity.
- Custom attributes (`LabeledEditText` styleable): `android:hint`, `android:inputType`, `android:drawableRight`, `underLine`; includes a monospace-font fix that resets the typeface for password input types.
- `visualAscent()` / `visualDescent()` and related: VisualMargin contract, measuring real font ascent of the inner field for tight row alignment.

## Junior notes
- The floating label comes free from TextInputLayout: set the label via `bind().label()` (outer hint), never by setting a hint on the inner EditText, or you get doubled hints.
- Focus callbacks give you the LabeledEditText as `v`, not the inner EditText; call `bind().text()` or keep another path to read input rather than casting `v` to EditText.
- Error styling is just the `activated` state flag on both children (`isErrorState()` reads it back), so state-list colors keyed on `state_activated` drive the red look.
