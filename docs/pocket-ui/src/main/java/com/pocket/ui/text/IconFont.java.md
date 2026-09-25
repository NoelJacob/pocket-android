# pocket-ui/src/main/java/com/pocket/ui/text/IconFont.java

## What this is

A tiny helper that embeds Pocket icon glyphs (arrow, diamond, Pocket logo) inline in a line of text. The icons live in the `pocket_icons.ttf` icon font, where each icon is a single private-use character (`\uE800`–`\uE802`); inserting that character with the icon `Typeface` renders the picture scaled like surrounding text. The class inserts the character at a position in a `SpannableStringBuilder` (a mutable rich-text buffer) and covers just that character with a `CustomTypefaceSpan`.

## How it fits

Any screen that wants an icon flowing with text — e.g. a label ending in an arrow — builds its string in a `SpannableStringBuilder`, calls `addArrow / addDiamond / addPocket`, and sets the result on a `TextView`. The private `addIcon()` does the real work: it fetches the icon font via `Fonts.get(context, Font.ICONS)` and applies `Spannable.SPAN_INCLUSIVE_INCLUSIVE` (a span flag meaning the span grows if text is typed at either edge) over the one inserted character.

## Key pieces

- `addArrow / addDiamond / addPocket(context, builder, position)` — WHY: named, readable entry points so callers never hardcode the `\uE800`-style codepoints themselves.
- `addIcon(context, builder, position, iconChar)` — WHY: the shared implementation; inserts the glyph and attaches the `CustomTypefaceSpan` so only that one character uses the icon font.

## Junior notes

- `position` is an index into the builder at call time: insert left to right and remember earlier inserts shift later indices.
- Because the icon is a font glyph, it follows the `TextView`'s text size and color — that is the point; for a fixed-size standalone icon use an `ImageView` or drawable instead.
