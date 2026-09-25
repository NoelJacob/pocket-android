# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedTextView.java
## What this is
Pocket's standard text view (stock AppCompatTextView plus theming, fonts, and link handling). For the user it is normal text; what it adds over stock is theme-aware text color, a `typeface` attribute for Pocket fonts, visual-padding options for pixel-exact spacing, and a link movement method that only eats taps actually on links.

## How it fits
Nearly every pocket-ui layout uses this instead of TextView. `applyTextAppearanceFromAttributes` resolves `typeface`, `visualPadding` / `paddingVerticalCenter`, and `compatTextColor` (via NestedColorStateList) from XML or text appearance. `setMovementMethodForLinks(true)` installs FixedLinkMethod so taps on plain text pass through to views behind. It also implements VisualMargin, reporting font ascent/descent so VisualMarginConstraintLayout can space text exactly.

## Key pieces
- `applyTextAppearanceFromAttributes()` — reads typeface, padding modes, and compat text color; called from the constructor and setTextAppearance so XML and code styling match.
- `setBold()` — swaps between Graphik bold and regular fonts.
- `setMovementMethodForLinks()` / `FixedLinkMethod` — link handling that returns false for non-link taps (letting them fall through) and supports PressableSpan pressed states.
- `onTouchEvent()` — with FixedLinkMethod installed, only consumes the event when a link was actually touched; clears pressed spans on up/cancel.
- `visualAscent()` / `visualDescent()` — expose font ascent/descent plus padding for visual-margin math; prepare methods are no-ops.
- `drawableStateChanged()` — forces invalidate so backgrounds repaint even when the text color itself did not change.
- `setTextAndUpdateEnUsLabel()` — sets text (label plumbing for locale tooling).

## Junior notes
- Stock LinkMovementMethod swallows all touches on the TextView; that is why the FixedLinkMethod workaround exists, enable it for text over clickable content.
- `compatTextColor` (not stock textColor) is what carries theme-state lists; edit-mode preview skips resolving it, so check colors on device.
