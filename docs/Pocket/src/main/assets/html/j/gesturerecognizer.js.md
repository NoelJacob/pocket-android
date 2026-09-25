# Pocket/src/main/assets/html/j/gesturerecognizer.js

## What this is

This is a vendored JavaScript port of Apple's iOS gesture recognizers (states like possible, began, changed, ended) by Takashi Okamoto, about 1040 lines. It turns raw touch or mouse events into higher-level gestures such as taps and swipes for pages that need them. The header warns it contains Pocket modifications, so it must be diffed against version 1.0 before any update.

## How it fits

Loaded by all three `article-mobile*.html` shells so article view can recognize taps and swipes inside the `BaseWebView` hosted by `ArticleFragment`. It requires either jQuery or Prototype to be present, which is why `j/jquery-3.4.1.min.js` loads first, and it builds on the `Class` helper from `j/jquery-inheritance.js`. Entries: gesture state constants, touch and mouse event mapping (mouse fallback on non-mobile user agents), the jQuery/Prototype framework shim, and the recognizer classes consumed by `j/articleview-mobile.js`.
