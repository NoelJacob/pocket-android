# Pocket/src/main/assets/html/c/video-article.css

## What this is

This is a tiny stylesheet that tones down embedded video placeholders inside articles. It paints `.video_box` light grey instead of black and forces the play icon background to black, with a slightly different grey when the dark theme (`body[textStyle="1"]`) is active.

## How it fits

Loaded by all three `article-mobile*.html` shells after `video/video.css`, so its rules override the default black video box that `video/video.html` uses for standalone playback. The video boxes themselves are created by `VideoPlayer` in `video/video.js` when `j/articleview-mobile.js` injects article content.
