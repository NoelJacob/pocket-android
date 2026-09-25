# Pocket/src/main/java/com/pocket/sdk/image/rule/ResizeFitToHeight.java
## What this is
Resize rule that pins the output height and lets the width follow the aspect ratio. Upscaling is configurable per request.
## How it fits
Installed by `Image.Builder.fitHeight(height, upscaleEnabled)` for rows or banners with a fixed height. Sample size and scale are computed from the height dimension only; the sized-file name is `_0-<h>.jpg` (`0` marks "width unconstrained").
## Key pieces
- `getInSampleSize()` / `getScale()` — delegate to `calculateInSampleSize(fromHeight, mHeight)` / `calculateScale(...)`; width plays no role.
- `getResizedWidth()` / `getResizedHeight()` — scale both source dimensions by the height ratio so the aspect ratio is preserved.
## Junior notes
- `getWidthFileName()` returns `"0"` on purpose: the sized file is keyed by height alone, so two requests with the same height share one cached file regardless of source width.
