# Pocket/src/main/assets/html/video/video.css

## What this is

This stylesheet styles embedded video boxes: `.video_box` is the black container, `.video_box_play_icon` overlays the play image, `.video_thumbnail` shows a centered cover thumbnail, and `.video_box_playing` keeps the background black during playback. The play art comes from `../i/play.png`.

## How it fits

Loaded by `video/video.html` for standalone playback and by all three `article-mobile*.html` shells for in-article embeds, where `c/video-article.css` further overrides it with a grey article-friendly look. The classes are applied by `VideoPlayer` in `video/video.js` (`embedParent.addClass("video_box")` and the `video_box_play_icon` offline state).
