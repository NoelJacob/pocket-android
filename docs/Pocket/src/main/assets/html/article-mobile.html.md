# Pocket/src/main/assets/html/article-mobile.html

## What this is

This is the empty shell page for the phone article reader. It has an empty body and pulls in the reader stylesheets and scripts, then creates one global `article = new ArticleView()` object that native code drives with JavaScript calls. A WebView is an Android widget that renders a web page inside the app; here it renders saved articles with Pocket styling instead of the original website.

## How it fits

`ArticleFragment` loads this file into its `BaseWebView` as `file:///android_asset/html/article-mobile${formFactor}.html` (no suffix on phones), choosing the tablet or small-tablet variant on larger screens via `FormFactor.getClassKey`. `ArticleViewModel` supplies the article content and settings, which reach this page through `ArticleView.load()` / `loadCallback()` calls in `j/articleview-mobile.js`. Downstream it pulls in `c/text.css`, `video/video.css`, `c/video-article.css`, `c/highlighting.css`, the font sheets, and the scripts `j/articleview-mobile.js`, `video/video.js`, `j/highlighting.js`, `j/diff_match_patch.js`, `j/searchText.js` plus the jQuery, inheritance, gesture, and Flash-shim libraries.
