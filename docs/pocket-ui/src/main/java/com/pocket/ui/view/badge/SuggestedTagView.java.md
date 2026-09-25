# pocket-ui/src/main/java/com/pocket/ui/view/badge/SuggestedTagView.java

## What this is
A tappable tag suggestion chip shown in tag-picker or "suggested tags" UI. The user sees an outlined pill with the suggested tag name; tapping it typically adds that tag. Visually it differs from `TagBadgeView` (solid fill) by using a stroked outline box instead.

## How it fits
Tag-suggestion screens lay out a set of `SuggestedTagView`s, one per suggestion, and set each label with the normal `TextView.setText()` methods. It extends `TextBadgeView` (inheriting the badge sizing, single-line ellipsizing, and small-title text style) and only overrides `init()` to change the look. The Figma link in the source comment is the design spec for this chip.

## Key pieces
- `init()`: calls `super.init()` for sizing/text style, then installs a `ButtonBoxDrawable` background (touchable-area fill with a grey-5 outline stroke) and a grey-1 text color. WHY `setBackground` directly instead of `setBadgeColor`: it needs the stroked `ButtonBoxDrawable`, not the solid `BadgeDrawable` that `setBadgeColor` installs.

## Junior notes
- `init()` is the `TextBadgeView` customization hook: subclasses override it (it runs from every constructor) instead of repeating setup in three constructors.
- `ButtonBoxDrawable` (in `view/button`) is the shared stroked-box background; `R.color` values here are theme-aware color lists, resolved via `ContextCompat.getColorStateList` so they follow light/dark theme.
