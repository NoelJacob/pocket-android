# Pocket/src/main/assets/html/j/jquery-inheritance.js

## What this is

This is John Resig's Simple JavaScript Inheritance helper (MIT licensed), 67 lines. It adds a global `Class.extend()` function that creates a class with a `_super` handle for calling the parent method, emulating classical inheritance before JavaScript had a `class` keyword. The `inheritanceLoaded` flag marks that the helper is present.

## How it fits

Loaded by all three `article-mobile*.html` shells and by `video/video.html` right after jQuery. `ArticleView` in `j/articleview-mobile.js` and `VideoPlayer` in `video/video.js` are both defined with `Class.extend()`, and `j/gesturerecognizer.js` also relies on the `Class` global. Entries: `Class` base constructor, `Class.extend(prop)` (prototype copying with `_super` wrapping).
