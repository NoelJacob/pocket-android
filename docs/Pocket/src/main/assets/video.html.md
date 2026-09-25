# Pocket/src/main/assets/video.html

## What this is

This is a small redirect page for opening videos outside article view. Its `tryToLoadVideo()` reads the video URL from the page's URL fragment (the part after `#`), and if the device is online it navigates there; otherwise it shows an alert that offline video is not supported. On load it tries silently, on tap it warns.

## How it fits

`StreamingMarkupProcessor` builds links to it as `file:///android_asset/video.html#<video-url>` when rewriting saved-page markup for offline reading. It talks to native code only through the `PocketAndroidWebInterface.isConnected()` bridge, and its play-button background historically matches `html/i/play.png` styling.
