# pocket-ui/src/main/java/com/pocket/ui/view/button/ErrorButton.java

## What this is
The destructive/error variant of the box button. For the user it looks exactly like the standard `BoxButton` — same shape, font, and padding — but filled with the error color (`pkt_button_box_error_fill`, a red) instead of teal, signaling a dangerous or failure-related action such as retrying after an error.

## How it fits
A sibling of `BoxButton` and `UpgradeButton`: all three extend `BoxButtonBase` (shared font/gravity/padding/clickable) and differ only in their `init` colors and background. Drop an `ErrorButton` into any layout or dialog where a `BoxButton` would go when the action needs the error treatment; enabled-dimming and state handling come free from the base class plus `ButtonBoxDrawable`.

## Key pieces
- `ErrorButton(context[, attrs[, defStyle]])` — standard View constructors for XML inflation or code creation.
- `init(context)` — WHY it exists: the entire variant in two lines — standard button text color plus a `ButtonBoxDrawable` filled with the error color resource. Comparing this with `BoxButton.init` shows the codebase's variant pattern: subclass = recolor.

## Junior notes
- There is no error-specific behavior here (no vibration, no extra states) — if you need a new visual variant, copy this file's shape and swap the color resource rather than adding flags to `BoxButton`.
