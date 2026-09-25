# Pocket/src/main/java/com/pocket/sdk/image/rule/ResizeFitToWidth.java
## What this is
Resize rule that pins the output width and lets the height follow the aspect ratio. Upscaling is configurable per request.
## How it fits
Installed by `Image.Builder.fitWidth(width, upscaleEnabled)` for full-width article images and list thumbnails. Sample size and scale come from the width dimension only; the sized-file name is `_<w>-0.jpg` (`0` marks "height unconstrained").
## Key pieces
- `getInSampleSize()` / `getScale()` — delegate to `calculateInSampleSize(fromWidth, mWidth)` / `calculateScale(...)`; height plays no role.
- `getResizedWidth()` / `getResizedHeight()` — scale both source dimensions by the width ratio so the aspect ratio is preserved.
## Junior notes
- Mirror image of `ResizeFitToHeight`: here `getHeightFileName()` returns `"0"`, so same-width requests share one cached file.
