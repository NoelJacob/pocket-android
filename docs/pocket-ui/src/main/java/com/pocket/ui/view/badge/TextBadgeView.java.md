# pocket-ui/src/main/java/com/pocket/ui/view/badge/TextBadgeView.java

## What this is
The base class for all text-label badge pills (`TagBadgeView`, `SuggestedTagView`). The user sees a fixed-height pill with small-title text that truncates with an ellipsis if too long, dimming when disabled. It owns the shared badge geometry: minimum height, side padding, and optical vertical centering of the text.

## How it fits
Nobody instantiates `TextBadgeView` directly for a final look; subclasses override `init()` to set their background and text colors, then rely on the inherited measuring and styling. It extends `ThemedTextView` (theme-aware `TextView`), paints its background through `BadgeDrawable` (installed by `setBadgeColor()`), and sizes itself with `IntrinsicSizeHelper` plus `BadgeUtil.getBadgeSize()`.

## Key pieces
- `init()`: the subclass hook — sets the `Pkt_Text_Small_LightTitle` appearance, single line with end-ellipsis, the badge minimum height via `IntrinsicSizeHelper`, and side/top padding tuned with font metrics so the text looks optically centered (ignoring the font's internal ascent gap, minus 1dp).
- `setBadgeColor(ColorStateList)`: installs a `BadgeDrawable` background in the given colors; returns `this` for chaining. Subclasses must call this (or `setBackground` for a custom drawable) plus `setTextColor`.
- `onMeasure(...)`: applies the `IntrinsicSizeHelper` width/height constraints before the normal `TextView` measuring, enforcing the badge minimum height.
- `setEnabled(boolean)`: dims the whole badge via `PktViewsKt.updateEnabledAlpha` in addition to the background's own disabled color.
- `visualAscent()/visualDescent()` returning 0: tells surrounding custom layouts (badge rows) that this view has no extra optical overhang to account for.

## Junior notes
- `ThemedTextView` re-resolves its themed text colors on theme change; extending it is what keeps badges correct in dark mode.
- Padding math uses `Paint.FontMetrics` (`top` vs `ascent`): `top` includes extra font spacing, `ascent` is the real glyph top; the formula centers on glyphs, not on the font box.
