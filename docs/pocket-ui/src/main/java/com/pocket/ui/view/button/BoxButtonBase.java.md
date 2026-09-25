# pocket-ui/src/main/java/com/pocket/ui/view/button/BoxButtonBase.java

## What this is
The shared foundation for all the old View-system box buttons (standard, error, upgrade, on-color). For the user it defines what every box button has in common: centered text in Pocket's Graphik medium font at medium size, comfortable visual padding, and a clickable text box. It is package-private (`class BoxButtonBase`, no `public`), so it can never be used from XML or other packages directly — you always pick one of its colored subclasses.

## How it fits
Sits in the middle of the chain: `CheckableTextView` (a TextView that can also hold a checked on/off state, used for toggle-like buttons) → `BoxButtonBase` (font, gravity, padding, alpha) → `BoxButton` / `ErrorButton` / `UpgradeButton` / `OnColorButton` (each just applies its own colors/background). `AppBar` and `InfoPagingView` consume the subclasses, never this class. It also clears the background to null so each subclass's `ButtonBoxDrawable` is the only background.

## Key pieces
- `init()` — WHY it exists: applies the four things every box button shares — clickable, centered gravity, Graphik medium typeface at `pkt_medium_text` size, and visual text padding (extra padding compensating for font whitespace so the label looks optically centered).
- `setEnabled(enabled)` — dims the button to 50% alpha via `PktViewsKt.updateEnabledAlpha` when disabled; WHY: one consistent "greyed out" treatment without per-variant disabled colors.
- `visualAscent() / visualDescent()` returning 0 — disables the parent's visual-margin adjustments (extra spacing based on font shape); WHY: the explicit `setVisualTextPadding` call already handles spacing, so the automatic one must be off to avoid double padding.

## Junior notes
- `CheckableTextView` means box buttons technically support a checked state even though most usages just treat them as momentary tap targets — don't add check logic here unless the design calls for a toggle.
- The `/// TODO ripple?` comment means there is no touch ripple yet; pressed feedback comes from the `ButtonBoxDrawable` state colors, not from a ripple drawable.
