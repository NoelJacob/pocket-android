# Pocket/src/main/java/com/pocket/util/android/view/ForegroundDrawableHelper.java
## What this is
A delegate that backports a foreground drawable (an overlay graphic drawn above a view's content, like a ripple) to custom view groups that predate native foreground support. It handles bounds, state, touch hotspots, and drawing for one overlay drawable.
## How it fits
Embedded in `ResizeDetectLinearLayout` and `ResizeDetectRelativeLayout`, each of which owns an instance and forwards its view lifecycle (`onSizeChanged`, `dispatchDraw`, `verifyDrawable`, touch, drawable state) to the matching `onParent...` method. Touch handling delegates further to `DrawableUtil.setHotspot()`. It draws the overlay onto the parent's canvas.
## Key pieces
- `ForegroundDrawableHelper(parent)` — WHY: binds the helper to its host view for callbacks and invalidation. Usage in words: create once as a field during view construction.
- `setForegroundDrawable(drawable)` — WHY: swaps the overlay at any time, detaching the old callback and invalidating. Usage in words: set or clear the ripple/selector drawable whenever content changes.
- `onParentSizeChanged(...)` — WHY: stretches the overlay to the new view bounds. Call from the host's `onSizeChanged`.
- `onParentDrawableStateChanged()` / `onParentJumpDrawablesToCurrentState()` / `onParentVerifyDrawable(who)` — WHY: keeps stateful overlays (pressed/disabled tints) in sync with the host. Call from the matching host overrides.
- `onParentTouchEvent(event)` — WHY: moves the ripple hotspot to the finger. Call from the host's `onTouchEvent`.
- `onParentDispatchDraw(canvas)` — WHY: paints the overlay above children. Call at the end of the host's `dispatchDraw`.
- `Setter` — WHY: tiny interface so generic code can set a foreground on either layout type.
## Junior notes
- All `onParent...` methods are null-safe; forwarding them unconditionally is correct even when no foreground is set.
- The drawable's callback is set to the parent view, so invalidating the drawable invalidates the view; never set a different callback yourself.
- Forgetting `verifyDrawable` forwarding breaks `invalidateDrawable` routing and the overlay will silently stop redrawing.

