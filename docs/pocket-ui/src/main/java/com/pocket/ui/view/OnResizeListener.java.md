# pocket-ui/src/main/java/com/pocket/ui/view/OnResizeListener.java

## What this is
A tiny callback interface for views that change size: `onViewSizeChanged(view, newWidth, newHeight, oldWidth, oldHeight)` fires whenever the observed view's dimensions change. It exists so a parent or helper that cannot subclass the view can still react to resizes (repositioning siblings, adjusting padding). It carries no logic — just the contract.

## How it fits
Custom views accept an `OnResizeListener` and invoke it from their size-change callbacks (typically `onSizeChanged()`), passing themselves plus the new and old dimensions. Any screen or container that needs to track a child view's size implements the interface and registers itself as the listener.

## Key pieces
- `onViewSizeChanged(v, newWidth, newHeight, oldWidth, oldHeight)`: the single callback — WHY all four dimensions are passed (not just the new ones) is so the receiver can tell growth from shrinkage and compute deltas without caching the previous size itself.

## Junior notes
- This is a plain Java interface, not an Android framework class — you implement it with an anonymous class or lambda-style object, and the observed view decides when to call it.
- Size callbacks can fire many times during layout or animation, so keep implementations cheap and avoid triggering another layout pass inside the callback (that causes a layout loop).
