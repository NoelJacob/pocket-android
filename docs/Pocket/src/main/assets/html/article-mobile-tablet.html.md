# Pocket/src/main/assets/html/article-mobile-tablet.html

## What this is

This is the empty shell page for the tablet article reader. It is identical to `article-mobile.html` except for one extra stylesheet, `c/text-tablet.css`, which widens margins and adjusts type for large screens. Like the phone version it has an empty body and creates a single global `article = new ArticleView()` for native code to drive.

## How it fits

`ArticleFragment` picks this file over `article-mobile.html` when `FormFactor.getClassKey` returns a tablet key, loading it into its `BaseWebView` as `file:///android_asset/html/article-mobile-tablet.html`. Everything else flows exactly as on phones: `ArticleViewModel` feeds content and display settings through `ArticleView.load()` / `loadCallback()`, and the page pulls in the same `j/articleview-mobile.js`, `video/video.js`, `j/highlighting.js`, `j/searchText.js`, and `c/highlighting.css` stack, with `c/text-tablet.css` layered on top of `c/text.css`.
