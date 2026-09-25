# Pocket/src/main/assets/html/article-mobile-smalltablet.html

## What this is

This is the empty shell page for the small-tablet article reader. It matches `article-mobile-tablet.html` plus one further stylesheet, `c/text-smalltablet.css`, which un-floats article media for mid-size screens. It likewise has an empty body and creates the global `article = new ArticleView()` object.

## How it fits

`ArticleFragment` loads this file into its `BaseWebView` when `FormFactor.getClassKey` returns the small-tablet key (`article-mobile-smalltablet.html`). Content and settings arrive from `ArticleViewModel` via `ArticleView.load()` / `loadCallback()` in `j/articleview-mobile.js`, and the page uses the same script stack as the other two shells, with styles layered `text.css`, then `text-tablet.css`, then `text-smalltablet.css`, plus `video/video.css`, `c/video-article.css`, and `c/highlighting.css`.
