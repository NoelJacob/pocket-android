# Pocket/src/main/assets/html/video/video.js

## What this is

This script plays embedded videos, about 490 lines defining the `VideoPlayer` class via `Class.extend`. It supports seven source types (`VIDEO_YOUTUBE`, `VIDEO_VIMEO_*`, `VIDEO_HTML5`, `VIDEO_FLASH`, `VIDEO_IFRAME`, `VIDEO_BRIGHTCOVE`), fits each player into a requested display size while preserving aspect ratio, shows a tappable play icon with an offline error dialog when there is no connection, and notifies callers through ready and resize listeners. A controller here is a small per-type object exposing play and control methods.

## How it fits

Loaded by all three `article-mobile*.html` shells and by `video/video.html`; `j/articleview-mobile.js` creates players for videos found in article content, and `video.html`'s `loadVideo()` creates one for fullscreen playback. It uses `PocketAndroidVideoInterface` to check connectivity, theme, and HTML5 video sizing, and `swfobject.js` for the legacy Flash path. Entries: `init()` (placeholder setup), `setDisplaySize()` (fit-to-box sizing), `load()` (online embed versus offline play-icon retry), `loadEmbed()` (per-type dispatch), `loadYoutubeEmbed()` / `loadVimeoEmbed()` / `loadHTML5Embed()` / `loadFlashEmbed()` / `loadIFrameEmbed()` (per-source players), `setOnReadyListener()` / `setOnResizeListener()` (callbacks).
