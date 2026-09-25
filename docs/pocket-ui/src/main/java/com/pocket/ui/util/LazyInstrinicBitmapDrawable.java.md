# pocket-ui/src/main/java/com/pocket/ui/util/LazyInstrinicBitmapDrawable.java

## What this is
The `wrap_content`-capable sibling of `LazyBitmapDrawable` (note the historical typo "Instrinic" in the class name). Same async bitmap loading, but once the image arrives it reports the bitmap's real pixel size as its intrinsic size and asks the host view to remeasure. That makes it usable as an `ImageView` source where the view sizes itself from the image.

## How it fits
`ImageView`s with `wrap_content` in one or both axes set this as their drawable instead of `LazyBitmapDrawable`. It fires an initial `fill(0, 0)` at construction (no bounds known yet), then when the bitmap lands it records the density-scaled dimensions and calls the `ViewSizeUpdater` so the `ImageView` remeasures around the now-known size. Placeholder painting while loading works the same way via `LazyBitmapDrawable.SupportsPlaceholder`.

## Key pieces
- Constructor `(context, lazy, updater)`: the `Context` is needed to scale bitmap pixels by screen density; the updater callback is how the drawable tells the view to remeasure, since a drawable cannot resize its host directly.
- `setBitmap(Bitmap)`: converts bitmap pixels to display pixels with `getScaledWidth/Height(densityDpi)`, stores them as `width`/`height`, repaints, and fires the updater. Density scaling is what keeps the image physically the same size across screen densities.
- `getIntrinsicWidth/Height`: return the loaded size, or -1 (no opinion) before anything loads. Returning -1 pre-load keeps the view collapsed rather than measured at zero.
- `draw(Canvas)`: identical policy to the non-intrinsic variant: bitmap when present, placeholder delegation otherwise.
- `ViewSizeUpdater` plus `IMAGE_VIEW`: the remeasure hook. The stock `IMAGE_VIEW` implementation clears and resets the drawable on the `ImageView`, which forces it to drop its cached drawable dimensions and remeasure. Custom containers can supply their own updater (for example calling `requestLayout`).

## Junior notes
- The class name misspells "Intrinsic" as "Instrinic". Do not "fix" it with a rename without updating every reference; the typo is the public API name.
- Resetting the drawable inside `IMAGE_VIEW` runs during layout-sensitive code, so custom updaters must be careful to request layout rather than mutate the view tree synchronously in ways that recurse.
