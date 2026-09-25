# Pocket/src/main/java/com/pocket/util/android/drawable/DrawableUtil.java
## What this is
A one-method static helper that positions a ripple/reveal effect at the user's finger. On `ACTION_DOWN` (the moment a finger touches the screen) it forwards the touch x/y into `Drawable.setHotspot()`, which is the point a ripple drawable expands from.
## How it fits
Called by `ForegroundDrawableHelper.onParentTouchEvent()`, which every `ResizeDetectLinearLayout` and `ResizeDetectRelativeLayout` delegates its touch events to. So any foreground ripple on those layouts starts exactly where the user tapped. It produces nothing downstream; it just mutates the passed drawable.
## Key pieces
- `setHotspot(drawable, event)` — WHY: makes ripples originate at the touch point instead of the view center. Usage in words: from a view's `onTouchEvent`, pass its foreground drawable and the motion event; null drawables and non-DOWN actions are safely ignored.
## Junior notes
- `setHotspot` only exists from API 21 (Lollipop); this codebase targets newer, so no version guard is needed.
- Only `ACTION_DOWN` matters because the ripple origin is fixed at touch start; later MOVE/UP events are intentionally ignored.

