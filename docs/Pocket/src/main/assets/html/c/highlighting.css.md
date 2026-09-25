# Pocket/src/main/assets/html/c/highlighting.css

## What this is

This stylesheet colors user highlights and find-in-page matches inside article view. It defines CSS variables for the palette and three span classes: `.highlight` for saved highlights, `.text_search_highlight` for search matches, and `.selected_highlight` for the currently selected match, each with a light-theme (`body[textStyle="0"]`) and dark-theme (`body[textStyle="1"]`) variant. The link-underline tricks keep highlighted links readable.

## How it fits

Loaded by all three `article-mobile*.html` shells after `text.css`. Its classes are produced at runtime by `j/highlighting.js` (`pktHighlighter` wraps annotation text in `.highlight` spans) and `j/searchText.js` (wraps find matches in `.text_search_highlight` / `.selected_highlight` spans, driven by `FindTextViewModel`). The `textStyle` attribute on `body` is set from native display settings through `ArticleView.newTextStyle()`.
