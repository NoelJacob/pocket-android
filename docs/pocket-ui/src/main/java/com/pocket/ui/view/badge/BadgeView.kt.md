# pocket-ui/src/main/java/com/pocket/ui/view/badge/BadgeView.kt

## What this is
A single chip (badge) showing a small icon plus a text label, used for tags and highlights on items. The user sees a compact pill with a tag icon and the tag name, or a highlight icon and highlight text. Appearance is picked by `Type`: grey tag pill, emphasized (highlighted) tag pill, or amber highlight pill.

## How it fits
Screens and rows (notably `BadgeLayout`, which lays out rows of these) create a `BadgeView` and call `setValues()` with the type and label. `BadgeView` extends `ThemedLinearLayout` (a theme-aware horizontal layout) and inflates `ViewBadgeBinding` (databinding = the `view_badge.xml` layout bound to fields, giving `binding.text` and `binding.icon`) as its content. It is the newer databinding-based badge; the older `TextBadgeView` family is a separate inheritance chain.

## Key pieces
- `setValues(Type, String)`: the only configuration point — swaps the background drawable (`bg_badge_tag`, `bg_badge_tag_emphasized`, `bg_badge_highlights`), tints the text and icon to the matching foreground color, sets the icon drawable, then applies the label and 5dp padding on all sides.
- `Type` enum (`TAG`, `EMPHASIZED_TAG`, `HIGHLIGHT`): selects which background/foreground/icon trio `setValues` applies. WHY an enum: callers pick a semantic role, and the view owns the exact drawables and colors.
- `binding` (`ViewBadgeBinding`): inflated with a `<merge>` root, so the code sets `orientation`/`gravity` in `also` after inflation; exposes `text` and `icon` views.

## Junior notes
- `ThemedLinearLayout` is the pocket-ui theme-aware `LinearLayout`; it re-applies themed colors when the app theme changes, which is why badge views extend it instead of the stock layout.
- `ContextCompat.getDrawable/getColorStateList` are the safe way to load resources that work across Android versions; drawables loaded this way may be shared, so never mutate them without `mutate()`.
- `5f.toPxInt(context)` is a pocket-ui extension converting dp floats to int pixels.
