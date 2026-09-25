# pocket-ui/src/main/java/com/pocket/ui/util/ColorStateListDrawable.java

## What this is
A `Drawable` (a drawing instruction Android can use as a background or image) that fills its bounds with a solid color picked from a `ColorStateList` (a color that changes with view state such as pressed, disabled, or checked) for the current state. Stock `ColorDrawable` only holds one fixed color, so this is the state-aware replacement. It optionally draws with rounded corners.

## How it fits
Views and button backgrounds that need a state-tinted solid fill create one of these instead of a `ColorDrawable` or an XML shape. Used as a view background or button drawable; whenever the view's state changes, `onStateChange` re-resolves the color and the view repaints with the right variant.

## Key pieces
- Constructors from a color-state-list resource id or a `ColorStateList`: the resource-id forms exist so callers pass `R.color/...` directly without resolving the list themselves.
- `cornerRadius` constructor plus `mRoundedCornerRect`: when a radius is given, draws a round rect instead of a plain rect. Caches the `RectF` (a float-precision rectangle) at construction and syncs it in `onBoundsChange`, so per-frame allocation is avoided.
- `onStateChange(int[])`: looks up `mColors.getColorForState(state, ...)` and repaints when it differs. Returning true tells Android the drawable changed and needs a redraw.
- `isStateful()` returning true: declares that this drawable reacts to state changes, which is what makes Android forward state changes to it at all.
- `getOpacity()` returning `TRANSLUCENT`: a conservative answer (the real opacity depends on the current color) so compositing stays correct.

## Junior notes
- A `Drawable` only receives state changes if `isStateful()` returns true and it is set as a view background or image; a standalone instance never updates on its own.
- `setAlpha` stores `mAlpha` and reapplies it in `onStateChange`, because resolving a new state color would otherwise wipe out the alpha. Order matters: color first, then alpha.
