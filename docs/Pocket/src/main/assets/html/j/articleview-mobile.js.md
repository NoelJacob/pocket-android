# Pocket/src/main/assets/html/j/articleview-mobile.js

## What this is

This is the main controller for article view, about 1830 lines defining the `ArticleView` class (built with the `Class.extend` helper from `j/jquery-inheritance.js`). It receives the reader settings from native code in `load()`, injects article HTML into the empty shell in `loadCallback()`, sizes and embeds videos, reports scrolling back to Android through `PocketAndroidArticleInterface`, applies font, theme, alignment, and line-height changes, and parses speakable text for text-to-speech. A JavaScript bridge here means a native object Android injects into the page so web code can call app code.

## How it fits

Instantiated once as `var article = new ArticleView()` by each `article-mobile*.html` shell, which `ArticleFragment` loads into its `BaseWebView`; `ArticleViewModel` provides the content and events that native code forwards into `load()`, `loadCallback()`, and the `new*` setting methods. It drives `video/video.js` for embeds, `j/highlighting.js` for saved highlights, and `j/searchText.js` for find-in-page, and its speech-parsing logic is mirrored natively by `ArticleUtteranceParser.java`. Entries: `load()` (one-time native settings init), `loadCallback()` (article HTML injection), `newFontSize` / `newFontType` / `newTextAlign` / `newLineHeightSetting` / `newTextStyle` (display settings), `scrolled()` (scroll reporting), video/image bookkeeping, TTS text extraction.
