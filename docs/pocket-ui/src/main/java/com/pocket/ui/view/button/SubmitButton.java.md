# pocket-ui/src/main/java/com/pocket/ui/view/button/SubmitButton.java

## What this is
A full-bleed, sharp-cornered submit bar button. For the user it is a flat edge-to-edge strip (no rounded corners) with centered white text — the "submit" affordance pinned to the bottom of forms and editors rather than the floating rounded `BoxButton`. It extends `ThemedTextView` (Pocket's theme-aware TextView that re-tints itself on light/dark changes) instead of `BoxButtonBase`, because its look (square, white-on-fill) predates/diverges from the box family.

## How it fits
Placed at the bottom of form-like screens and dialogs where the action belongs to the container edge (the `CornerStyle` equivalent here is "no rounding at all" — radius 0). Like its cousins it uses Graphik medium at medium size, visual text padding, a `ButtonBoxDrawable` background (with `pkt_button_box_fill` and corner radius 0), and dims to half alpha when disabled via `PktViewsKt.updateEnabledAlpha`. There are no databinding adapters — screens set text/click listeners directly.

## Key pieces
- `init()` — the whole look in one method: centered Graphik-medium label, white state-aware text color, square `ButtonBoxDrawable` background. WHY radius 0 is passed explicitly: the drawable defaults to 4dp rounding, and this variant must opt out.
- `visualAscent() / visualDescent()` returning 0 — same as `BoxButtonBase`: disables automatic visual-margin compensation because explicit `setVisualTextPadding` already handles optical centering.
- `setEnabled(enabled)` — 50%-alpha dimming when disabled, shared convention with every other button in this package.

## Junior notes
- It is *not* a `BoxButtonBase` subclass, so a helper typed to `BoxButtonBase` won't accept it — if you need polymorphic handling of "any bottom action button", use `TextView`/`View` as the common type.
- The `/// TODO ripple?` comment applies here too: no touch ripple yet, pressed feedback comes from the background drawable's state colors.
