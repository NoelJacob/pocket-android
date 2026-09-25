# Pocket/src/main/assets/video_embed.html

## What this is

This is a blank black fullscreen page with a mobile viewport tag and no scripts. It is a neutral container: full-size black body with zero margin, ready for native code or an iframe to inject a video embed.

## How it fits

It sits alongside `video.html` in the assets bundle as the empty counterpart to that redirect page. No direct caller was found in the current sources; it likely serves as a generic embed target for video playback flows that fill the body at runtime. If no loader turns up, it is a candidate for deletion.
