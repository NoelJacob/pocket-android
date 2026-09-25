# Pocket/src/main/java/com/pocket/util/android/drawable/SimpleStatefulDrawable.java
## What this is
An abstract base class for hand-drawn custom drawables that react to view state (pressed, focused, disabled). Subclasses register their `Paint` objects once, and this class forwards alpha, color filters, and state changes to all of them, redrawing on every state change.
## How it fits
Extended by tooltip chrome such as `com.pocket.sdk.util.view.tooltip.view.CaretTooltip`, which draws its bubble background through here. State flows in from the view system via `onStateChange()` and out to each registered `StatefulPaint` (a `Paint` whose color follows a color-state list). It sits above `android.graphics.drawable.Drawable` and below concrete shapes.
## Key pieces
- `registerPaint(paint)` — WHY: enrolls a paint for all future forwarding and enables anti-aliasing (smooth edges) in one place. Usage in words: call once per paint in the subclass constructor.
- `setAlpha(alpha)` / `setColorFilter(cf)` — WHY: `Drawable` contract; fans the value out to every registered paint so fades and tints apply uniformly.
- `getOpacity()` — WHY: reports TRANSLUCENT so the framework composites correctly.
- `isStateful()` / `onStateChange(state)` — WHY: declares state-awareness and pushes new states into any `StatefulPaint`, then invalidates (requests redraw).
## Junior notes
- Abstract with no `draw()` of its own: subclasses MUST implement `draw(Canvas)`; this class only handles state plumbing.
- `onStateChange` returns true unconditionally, meaning "I changed"; that forces a redraw even if colors matched, which is simple but slightly wasteful.

