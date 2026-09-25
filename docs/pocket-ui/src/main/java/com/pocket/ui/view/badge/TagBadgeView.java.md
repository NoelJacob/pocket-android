# pocket-ui/src/main/java/com/pocket/ui/view/badge/TagBadgeView.java

## What this is
The standard solid tag chip: a small pill with the tag name in it, as seen next to saved items. The user just reads the label (set with normal `TextView.setText()`); the view supplies the badge sizing, background fill, and text colors.

## How it fits
Item rows and detail screens drop a `TagBadgeView` into their layout wherever a tag should appear. It extends `TextBadgeView`, inheriting the badge minimum height, single-line ellipsize behavior, and disabled-state dimming, and only customizes colors in `init()`. It is the filled counterpart to `SuggestedTagView` (outlined suggestion chip).

## Key pieces
- `init()`: calls `super.init()`, then applies the tag fill via `setBadgeColor()` (theme-aware `pkt_badge_tag` list, which installs a `BadgeDrawable` background) and the tag foreground via `setTextColor()` (theme-aware `pkt_badge_tag_text`). All three constructors funnel through here.

## Junior notes
- `NestedColorStateList.get()` resolves a color that may itself reference themed attributes; prefer it over `ContextCompat` when the color XML uses theme references.
- Because `init()` runs inside the constructor, only touch state set up by the superclass or fresh objects here; anything needing constructor arguments must wait.
