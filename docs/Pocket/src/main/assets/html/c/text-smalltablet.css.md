# Pocket/src/main/assets/html/c/text-smalltablet.css

## What this is

This is a one-rule stylesheet override for small tablets. It forces `#RIL_media` (the article's side media container) to not float, with a fixed margin, so images and embeds stack below text instead of beside it on mid-size screens.

## How it fits

Loaded only by `article-mobile-smalltablet.html`, after `c/text-tablet.css`, so this single rule wins over the tablet defaults. It works together with `c/text.css`, which defines the base `#RIL_media` layout for phones.
