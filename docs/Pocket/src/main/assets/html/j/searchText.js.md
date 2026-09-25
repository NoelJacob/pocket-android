# Pocket/src/main/assets/html/j/searchText.js

## What this is

This script implements find-in-page highlighting for article view as a small jQuery plugin, adapted from Johann Burkard's highlight plugin. `highlightSearchTerm()` walks text nodes and wraps each case-insensitive match in a `.text_search_highlight` span, `removeSearchTermHighlights()` unwraps them and re-merges split text, and `setCurrentSelection()` marks one match as `.selected_highlight` and returns it for scrolling.

## How it fits

Loaded by all three `article-mobile*.html` shells and styled by `c/highlighting.css`. It is driven from the native side by the find-text flow (`FindTextViewModel` in `ArticleFragment`'s reader package), which calls into the page to highlight matches and step through them. Entries: `highlightSearchTerm()` (wrap matches), `removeSearchTermHighlights()` (unwrap and normalize), `setCurrentSelection()` (mark and return the active match).
