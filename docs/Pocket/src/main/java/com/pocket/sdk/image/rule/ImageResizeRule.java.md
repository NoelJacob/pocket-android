# Pocket/src/main/java/com/pocket/sdk/image/rule/ImageResizeRule.java
## What this is
Abstract recipe for one resize strategy: how much to downsample at decode time (`inSampleSize`), how much to scale after that, what the final dimensions are, and what file-name suffix the sized file gets. The `mAllowUpScale` flag decides whether small images may be enlarged.
## How it fits
Chosen by `Image.Builder` (`fill` / `fit` / `fitWidth` / `fitHeight` each installs a subclass) and stored on `Image.Request.resize`. `ImageResizer` calls `getInSampleSize()` during decode and `getScale()` / `getResizedWidth()` / `getResizedHeight()` for the final bitmap; `getWidthFileName()` / `getHeightFileName()` build the `_w-h.jpg` sized-file name.
## Key pieces
- `getInSampleSize(fromWidth, fromHeight)` — power-of-2 downsample factor so decoding reads fewer pixels; each subclass picks which dimension drives it.
- `getScale(fromWidth, fromHeight)` — post-decode float scale to reach the target; returns 1 (no scaling) when upscaling is disallowed and the source is already smaller.
- `calculateInSampleSize(fromSize, toSize)` — shared loop halving the dimension until it fits; guards `toSize < 1` to avoid an infinite loop and clamps `toSize` to `fromSize` when upscaling is off.
- `calculateScale(fromSize, toSize)` — shared `to/from` ratio with the same no-upscale clamp.
- `Scaling` — tiny value holder pairing the `inSampleSize` with its post-sample scale.
## Junior notes
- `inSampleSize` must be a power of 2 for efficient `BitmapFactory` decoding; that is why a 3998px source targeting 2000px still decodes at 1999px and then scales, rather than sampling to exactly 2000.
