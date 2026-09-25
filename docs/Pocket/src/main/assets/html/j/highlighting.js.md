# Pocket/src/main/assets/html/j/highlighting.js

## What this is

This script renders saved user highlights inside article view. It exposes a `pktHighlighter` module whose `highlightAnnotation()` wraps the annotated passage in `.highlight` spans (styled by `c/highlighting.css`) and wires taps on a highlight back to native code with the annotation JSON. It safely highlights across HTML tags and leans on `diff_match_patch.js` for fuzzy locating.

## How it fits

Loaded by all three `article-mobile*.html` shells after `j/articleview-mobile.js`, and called once article content is injected via `loadCallback()`. Taps report out through the `PocketAndroidArticleInterface.onHighlightClicked()` bridge into the reader layer (`ArticleFragment` / `ArticleViewModel`). Entries: `highlightAnnotation()` (entry point per saved annotation), `highlight()` (tag-safe span wrapping), `removeHighlight()` / `removeAllHighlights()` (clearing highlights).
