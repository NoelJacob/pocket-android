# pocket-ui/src/main/java/com/pocket/ui/view/button/ButtonBoxDrawable.java

## What this is
A custom `Drawable` (an object that knows how to paint itself into a View's background) that draws the rounded-rectangle box behind every box-style button. For the user it is the visible button shape: a filled rounded rect, optionally with an outline ring, that changes color when the button is pressed, checked, or disabled. It reads its colors from `ColorStateList` resources (XML files mapping view states to colors) via `NestedColorStateList`, so one drawable instance handles all states.

## How it fits
Instantiated by `BoxButton`, `ErrorButton`, `UpgradeButton`, `SubmitButton`, and the `PurchaseButton.Binder` (each passing its own fill/stroke color resources). It is set as the button's background; the framework calls `onStateChange` on every press/enable/check transition, and the drawable re-resolves its paint colors and redraws. Corner rounding comes from constructor args, defaulting to 4dp radius with a 1dp outline.

## Key pieces
- `ButtonBoxDrawable(context, fillColors, strokeColors[, cornerRadius[, outlineStroke, cornerStyle]])` — the overload chain; WHY: call sites only pass what they vary (most pass fill + stroke resource ids, `SubmitButton` passes a 0 radius for sharp corners). A `0` resource id means "no stroke".
- `CornerStyle { ALL, TOP, BOTTOM }` — WHY it exists: buttons docked to an edge (e.g. a submit bar) need square corners on one side; `TOP`/`BOTTOM` draw the rounded rect then paint a square patch over the flat side.
- `onStateChange(state)` → `updateDrawComponents()` — re-resolves fill/stroke for the new state and calls `invalidateSelf()` (requests a redraw) when the color actually changed, including a workaround forcing TextView parents to redraw when only the background changed.
- `draw(canvas)` — paints the outline rect first, then the inset fill rect. WHY rects instead of strokes: a code comment records that real stroke drawing rendered inconsistent corner curves on some devices (first-gen Nexus 7); two nested round-rects look identical everywhere.
- `setAlpha(alpha)` / `setColorFilter(...)` — standard Drawable contract; alpha is stored and reapplied to both paints (used for fade effects), color filter is intentionally a no-op.

## Junior notes
- `isStateful()` returns true — that is what makes the framework forward state changes to `onStateChange`; returning false would freeze the button in its initial color.
- `getOpacity()` returns TRANSLUCENT because state colors can include transparency; don't "optimize" it to OPAQUE or blending artifacts appear.
