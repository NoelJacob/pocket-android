# Pocket/src/main/java/com/pocket/sdk/image/rule/ResizeFillAndCrop.java
## What this is
Resize rule that guarantees an exact `minWidth x minHeight` output: scale until the smaller ratio fills the box, then center-crop the overflowing dimension. Always allows upscaling small images up.
## How it fits
Installed by `Image.Builder.fill(width, height)` for thumbnails and cover art that must fill a fixed frame. `ImageResizer` uses its sample size and scale, then crops; the sized file is named `_<w>-<h>.jpg` with both real values.
## Key pieces
- `getInSampleSize()` / `getScale()` — compare horizontal vs vertical ratios and drive off the larger one (the dimension that needs more scaling); remembers which axis to crop via `CROP_WIDTH` / `CROP_HEIGHT`.
- `getResizedWidth()` / `getResizedHeight()` — always return the requested minimums exactly, since cropping absorbs the remainder.
## Junior notes
- Unlike the `fit*` rules, this one passes `true` for upscaling in its constructor, so a tiny source is enlarged rather than left small; expect some softness on small originals.
