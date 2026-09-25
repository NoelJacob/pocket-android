# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedEditText.kt
## What this is
A text-input field (stock AppCompatEditText) that follows Pocket's light/dark theme and Pocket fonts. For the user it is a normal editable text box; what it adds over stock is theme-aware text/hint colors plus a `typeface` attribute for picking a Pocket font (see Fonts) from XML or text appearance.

## How it fits
Forms and search fields use this instead of EditText so input text stays readable in both themes. `init` and `setTextAppearance` both funnel through `applyTextAppearanceFromAttributes`, which resolves the `typeface`, `compatEditTextColor`, and `compatEditTextHintColor` attributes via NestedColorStateList (theme-aware color lookup). Theme-state merging in `onCreateDrawableState()` keeps the colors live across theme switches.

## Key pieces
- `applyTextAppearanceFromAttributes()` — reads typeface and the two compat color attributes and applies them; called from init and from setTextAppearance so styles work both ways.
- `setTextAppearance()` — deprecated platform overload, overridden so theme fonts/colors also apply when a text appearance style is set in code.
- `onCreateDrawableState()` — merges the theme attributes so the compat colors re-resolve on switch.
- Subpixel text flag in init keeps small text crisp.

## Junior notes
- `compat*` attributes exist because stock textColor cannot reference theme-state lists directly; NestedColorStateList bridges them.
- Both init and setTextAppearance must apply the attributes, otherwise XML-styled and code-styled instances would look different.
