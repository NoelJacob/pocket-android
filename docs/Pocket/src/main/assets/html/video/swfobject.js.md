# Pocket/src/main/assets/html/video/swfobject.js

## What this is

This is SWFObject v2.2 (MIT licensed), the classic helper for embedding Adobe Flash movies in a page. Flash is long dead on Android, so this is legacy support code kept for one old video path.

## How it fits

Loaded by all three `article-mobile*.html` shells and by `video/video.html`. Its only consumer is the `VIDEO_FLASH` branch of `VideoPlayer.loadFlashEmbed()` in `video/video.js`, which needs it in the page head; all other video types (YouTube, Vimeo, HTML5, iframe, Brightcove) ignore it.
