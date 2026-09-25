# Pocket/src/main/assets/html/video/video.html

## What this is

This is the standalone fullscreen video player page. It has a single `#playerwrapper` div and a `loadVideo(video)` function that builds a `VideoPlayer` in it, centers the player on resize events, and stores the ready controller for playback control.

## How it fits

Loaded in a WebView for fullscreen video playback, separate from the article shells. It pulls in jQuery, `j/jquery-inheritance.js`, `video/swfobject.js`, and `video/video.js`, then native code calls `loadVideo()` with the video descriptor; the player reports readiness through `setOnReadyListener` and talks to native code via `PocketAndroidVideoInterface`.
