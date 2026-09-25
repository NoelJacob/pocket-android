# sync-pocket-android/src/main/java/com/pocket/sync/value/AndroidParser.kt

## What this is

The Android HtmlString parser: converts stored HTML strings into Android Spanned rich text (the styled-text type TextViews render) via HtmlCompat.fromHtml in LEGACY mode. HtmlString is the engine's platform-agnostic HTML scalar; each platform provides a Parser turning it into native rich text, and this object is Android's. Article bodies and rich excerpts render through it.

## How it fits

Registered as the HtmlString.Parser for Android; article/readerview code calls parse and sets the resulting Spanned on TextViews. Other platforms supply their own parser for the same scalar.

## Key pieces

- `parse` — HTML-to-Spanned conversion using compat parsing for version-consistent rendering

## Junior notes

- FROM_HTML_MODE_LEGACY preserves historical rendering quirks: changing modes reflows every article, so treat it as a visual-compat commitment.
