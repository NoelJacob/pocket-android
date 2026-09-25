# pocket-ui/src/main/java/com/pocket/ui/util/LazyBitmap.java

## What this is
A contract for bitmaps (decoded images) that load asynchronously, sized for the space they will fill. Instead of blocking the UI thread decoding a large image, the producer renders or fetches the bitmap for the requested pixel size in the background and delivers it through a callback. Built to feed `LazyBitmapDrawable`.

## How it fits
Image-loading code (article thumbnails, placeholders, generated graphics) implements this interface. `LazyBitmapDrawable` and `LazyInstrinicBitmapDrawable` call `fill` with their current bounds; the implementation decodes or draws at that size and calls back with the finished `Bitmap`.

## Key pieces
- `fill(widthPx, heightPx, loaded, canceller)`: the single work request. Taking the target size lets the producer decode or render at exactly the displayed resolution instead of wasting memory on a full-size bitmap.
- `Loaded.onBitmapLoaded(Bitmap)`: delivery callback invoked when the image is ready. Decouples slow production from drawing, so the drawable just repaints when this fires.
- `Canceller`: a cooperative cancellation token. When bounds change or a view scrolls away, the drawable cancels the in-flight request so stale or oversized results are dropped instead of overwriting the current image.
- `Canceller.cancelAndRenew(canceller)`: cancels the previous token (if any) and returns a fresh one. Exists so the drawable can restart loading in one line on every bounds change without null checks.

## Junior notes
- The producer MUST check `canceller.isCancelled()` before delivering, otherwise a slow load for old bounds can clobber the image for the new bounds.
- Callbacks arrive on whatever thread the producer uses; the drawable side must handle the handoff to the UI thread before touching views.
