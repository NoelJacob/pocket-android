# Pocket/src/main/java/com/pocket/util/android/view/ResizeDetectLinearLayout.java
## What this is
A `LinearLayout` (a view group stacking children vertically or horizontally) that adds four behaviors: resize callbacks, a foreground overlay drawable, theme-aware dividers between children, and max-width capping. It is the workhorse container for themed rows and tooltip content.
## How it fits
Inflated from layouts such as `res/layout/view_tooltip_v3_simple.xml` and anywhere a themed linear container is needed; its styleable attributes (`dividerColor`, `dividerStroke`, `dividerInset`, `android:foreground`) come from `res/values/attrs.xml`. It composes `ForegroundDrawableHelper`, `MaxWidthHelper`, `StatefulPaint` dividers, and `OnResizeListener` callbacks, and merges Pocket theme states in `onCreateDrawableState()`. Downstream it draws dividers and the foreground in `dispatchDraw()`.
## Key pieces
- `init(attrs)` — WHY: reads divider colors/strokes and the foreground drawable from XML in one place.
- `onSizeChanged(...)` — WHY: fans size changes to the resize listener and the foreground helper's bounds. Usage in words: register via `setOnResizeListener()` to react to size changes.
- `setOnResizeListener(listener)` — WHY: the `ResizeDetectView` contract implementation.
- `onCreateDrawableState(...)` — WHY: merges the Pocket theme state so theme selectors apply to this container and children.
- `setDividerStroke(color, stroke, inset)` — WHY: enables state-aware separators drawn between children with the given thickness and end inset.
- `dispatchDraw(canvas)` — WHY: draws dividers (skipping gone siblings in horizontal mode) then the foreground overlay above children.
- Foreground delegation (`setForegroundDrawable`, `verifyDrawable`, `jumpDrawablesToCurrentState`, `onTouchEvent`, `drawableStateChanged`) — WHY: keeps the overlay's bounds, state, and ripple hotspot in sync; each forwards to `ForegroundDrawableHelper`.
- `getMaxWidth()` / `setMaxWidth(...)` / `onMeasure(...)` — WHY: caps row width on wide screens via `MaxWidthHelper`.
## Junior notes
- `StatefulPaint` dividers need `setDividerStroke` before they draw; the `mDrawDivider` flag defaults to false so no XML divider means no dividers.
- In horizontal orientation, the divider before a child is skipped when the previous sibling is `GONE`; in vertical mode all gaps draw, so hidden rows still leave a line.
- A `View` here means the Android UI widget base class; `dispatchDraw` draws children first via super, then decorations, so the foreground always sits on top.

