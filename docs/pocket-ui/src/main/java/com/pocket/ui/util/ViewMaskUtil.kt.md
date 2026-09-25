# pocket-ui/src/main/java/com/pocket/ui/util/ViewMaskUtil.kt

## What this is
Kotlin helpers that clip custom-view drawing into rounded rectangles, circles, or arbitrary shapes using offscreen layers and Porter-Duff blending (a compositing rule that combines two drawings pixel by pixel; `DST_IN` keeps the overlap, `DST_OUT` punches a hole). They save every custom view from hand-rolling save-layer/mask/restore sequences, which are easy to get wrong and to leak into surrounding content.

## How it fits
Custom views call these at the end of `onDraw` (Android's per-frame drawing method), after their content is already on the canvas. `roundCanvasCorners` gives cards and thumbnails rounded corners, `makeCircle` gives avatars and dots circular crops, and `maskCanvas` / `cutoutFromCanvas` cover bespoke shapes and punch-throughs.

## Key pieces
- `ViewMaskPaints.maskLayerPaint` (`DST_IN`) / `cutoutLayerPaint` (`DST_OUT`): shared paints carrying the compositing mode. Sharing avoids reallocating blend state per frame; the mode is the semantic difference between "keep inside the shape" and "remove the shape".
- `View.roundCanvasCorners(canvas, cornerRadius)`: masks the already-drawn content with a round rect of the view's full size. The convenience wrapper over `maskCanvas` for the most common case.
- `View.makeCircle(canvas, maskModifier)`: masks content to the largest centered circle, with an optional extra mask lambda for additions like rings or notches.
- `maskCanvas(canvas, mask)`: pushes a `DST_IN` layer, draws the shape, and composites, keeping canvas content only inside the shape. Documented as "call after drawing content" because anything drawn later lands outside the mask.
- `cutoutFromCanvas(canvas, cutout)`: the inverse with `DST_OUT`, turning the drawn shape transparent. Used for holes, scrims with windows, and badge cutouts.

## Junior notes
- Both core functions force the view into a hardware layer (`LAYER_TYPE_HARDWARE`) so the blend stays inside this view and does not erase siblings or the window behind it. That layer has a real GPU cost, so do not apply these to large views animating every frame without profiling.
- These are `View` extensions but operate on the passed `Canvas`; the view receiver supplies width/height and the layer. Passing a different canvas (for example an offscreen bitmap's) while relying on the view's dimensions masks the wrong region.
