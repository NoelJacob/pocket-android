# Pocket/src/main/java/com/pocket/sdk/image/rule/ResizeFitWithin.java
## What this is
Resize rule that shrinks an image to fit inside a `maxWidth x maxHeight` box while keeping the aspect ratio. Images already smaller than the box are left untouched (upscaling is always off).
## How it fits
Installed by `Image.Builder.fit(width, height)` for "show the whole image, but no bigger than this" cases like article bodies. Picks the tighter of the width/height ratios; the sized file is named `_<maxW>-<maxH>.jpg`.
## Key pieces
- `getInSampleSize()` / `getScale()` — compute both ratios and drive off the smaller one (the binding constraint), so both output dimensions end up at or under their maximums.
- `getResizedWidth()` / `getResizedHeight()` — source dimensions multiplied by that scale, preserving aspect ratio.
## Junior notes
- Constructor hardcodes `super(false)`: unlike `ResizeFillAndCrop`, this rule never enlarges; if you need small images blown up, use `fill` or a `fit*` variant with `upscaleEnabled=true`.
