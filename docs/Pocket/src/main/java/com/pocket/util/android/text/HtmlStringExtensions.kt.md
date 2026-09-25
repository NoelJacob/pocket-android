# Pocket/src/main/java/com/pocket/util/android/text/HtmlStringExtensions.kt
## What this is
Two Kotlin extensions that render a server-provided `HtmlString` (Pocket's HTML excerpt type) as styled Android text with search-hit highlighting. Italic spans (which the parser produces for the server's `<em>` match markers) are swapped for background-color highlight spans.
## How it fits
`toTealHighlightedSpannableString(theme, context)` is called by `com.pocket.app.list.list.MyListAdapter` to paint search matches teal in list rows, picking the dark- or light-theme teal (`pkt_dm_teal_1` vs `pkt_teal_5`). `toHighlightedSpannableString(highlightColor)` is the generic core it delegates to. It consumes `HtmlString` plus `AndroidParser` and produces a `CharSequence` for `TextView`s.
## Key pieces
- `HtmlString.toHighlightedSpannableString(highlightColor)` — WHY: converts italics-as-markers into real highlights with any color. Usage in words: parse the excerpt, then call with your highlight color to get displayable text.
- `HtmlString.toTealHighlightedSpannableString(theme, context)` — WHY: the theme-correct shortcut so list code never hardcodes colors. Usage in words: pass the current `Theme` and a context; dark mode gets the dark teal automatically.
## Junior notes
- Only `ITALIC` and `BOLD_ITALIC` style spans are treated as hits; genuine italic formatting from the server will also become highlights, which is a known tradeoff.
- The replacement span uses `SPAN_INCLUSIVE_INCLUSIVE`, so typing-adjacent edits keep the highlight; the original italic span is removed, not kept alongside.

