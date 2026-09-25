# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedImageView.kt
## What this is
An image view (stock AppCompatImageView) that tints its icon with a theme-aware color and can force a fixed width-to-height ratio. For the user it shows icons and images that automatically recolor for light/dark mode. What it adds over stock is the `drawableColor` attribute (a theme-state color list applied as a tint), the `heightRatio` attribute, and helpers to swap drawables and tint overrides in code.

## How it fits
Icon-heavy rows and buttons use this in XML with `drawableColor` pointing at a themed color list, so icons follow the theme with no code. `refreshDrawableState()` re-resolves the tint from the current drawable state on every state change. The `drawable` databinding adapter (a function letting XML attributes drive view properties; databinding means XML layouts bound to ViewModel fields) lets layouts set the image from a ViewModel. `setImageResourceTinted` re-sets drawables so the tint applies to the new image.

## Key pieces
- `drawableColor` / `mColors` — the theme-aware tint list, resolved in `applyDrawableColor()` against the current drawable state and applied as an SRC_IN color filter.
- `heightRatio` — when positive, `onMeasure` forces height = width times ratio (useful for fixed-aspect banners).
- `setDrawableColor()` / `setDrawableColorOverride()` — change the tint list or plug a custom per-state color function; only take effect on refresh or when the drawable is re-set.
- `setImageResourceTinted()` — null-safe drawable swap that re-triggers tinting.
- Companion `setDrawable()` — `@BindingAdapter("drawable")` for setting the image from databinding layouts.

## Junior notes
- Tint applies to the current drawable at refresh time; after changing colors you must re-set the drawable (or it happens via refreshDrawableState) for it to take effect.
- Layout preview skips NestedColorStateList (it crashes the preview renderer), so colors only resolve on a real device or emulator.
