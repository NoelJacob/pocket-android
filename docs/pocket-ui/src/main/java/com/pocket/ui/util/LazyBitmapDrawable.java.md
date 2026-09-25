# pocket-ui/src/main/java/com/pocket/ui/util/LazyBitmapDrawable.java

## What this is
A `Drawable` that shows an asynchronously loaded bitmap stretched to its bounds. It solves the "image not ready at layout time" problem: the drawable paints nothing (or a placeholder) immediately, kicks off a background `LazyBitmap.fill` for its exact pixel size, and repaints when the bitmap arrives. It deliberately reports no intrinsic size, so it only works where the hosting view already knows its bounds.

## How it fits
Views with fixed or parent-determined bounds (fixed-size thumbnails, banner slots) set this as their drawable. On every bounds change it cancels the old load and requests a fresh bitmap at the new size. While loading, it delegates to the host view if that view implements `SupportsPlaceholder`. For `wrap_content` image views that need the drawable to dictate size, use `LazyInstrinicBitmapDrawable` instead.

## Key pieces
- Constructor `(lazy)`: binds the async image source. The `onLoaded` callback is wired to the private `setBitmap`, which stores the result and calls `invalidateSelf` (asks Android to redraw the drawable).
- `onBoundsChange(Rect)`: drops the stale bitmap, cancels the in-flight request via `Canceller.cancelAndRenew`, and starts a new `fill` at the new width/height. Reload-on-resize is why images stay sharp after rotation or layout changes instead of stretching a stale bitmap.
- `getIntrinsicWidth/Height` returning -1: declares "I have no natural size". This is a deliberate contract: parents must size the view some other way, and it is the key difference from the intrinsic variant.
- `draw(Canvas)`: paints the loaded bitmap scaled to bounds, or forwards to `SupportsPlaceholder.drawPlaceholder` when no bitmap is ready yet.
- `SupportsPlaceholder`: interface the host view (the drawable's callback) can implement to paint a loading state in the same bounds and state. Keeps placeholder logic in the view and async logic in the drawable.
- `onStateChange`: returns true and repaints, assuming the placeholder is stateful, so pressed/disabled styling of the placeholder updates.

## Junior notes
- Never use this drawable inside a `wrap_content` `ImageView`: with no intrinsic size the view measures as zero. That is the most common misuse; reach for `LazyInstrinicBitmapDrawable`.
- `paint.setFilterBitmap(false)` keeps scaled drawing crisp for pixel-style placeholders; if your image looks blocky when scaled, this flag is why.
